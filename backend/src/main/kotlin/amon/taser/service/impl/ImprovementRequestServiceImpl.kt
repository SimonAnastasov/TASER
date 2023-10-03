package amon.taser.service.impl

import amon.taser.model.User
import amon.taser.model.AudioTranscription
import amon.taser.model.ImprovementRequest
import amon.taser.model.ImprovementResponse
import amon.taser.model.enums.ImprovementRequestStatusEnum
import amon.taser.model.enums.ImprovementResponseStatusEnum
import amon.taser.repository.TranscriptionRepository
import amon.taser.repository.ImprovementRequestRepository
import amon.taser.repository.ImprovementResponseRepository
import amon.taser.service.ImprovementRequestService
import amon.taser.service.ValidityCheckService
import amon.taser.service.OrderService
import amon.taser.service.TranscriptionService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*
import java.time.Instant
import java.time.Duration

@Service
class ImprovementRequestServiceImpl(
        val improvementRequestRepository: ImprovementRequestRepository,
        val improvementResponseRepository: ImprovementResponseRepository,
        val validityCheckService: ValidityCheckService,
        val orderService: OrderService,
        val transcriptionService: TranscriptionService,
        val transcriptionRepository: TranscriptionRepository,
): ImprovementRequestService {

    override fun getImprovementRequestFromId(id: UUID): ImprovementRequest? {
        return improvementRequestRepository.findById(id).get()
    }

    override fun requestImprovement(employer: User, transcriptionId: UUID, paymentIntent: Map<String, Any>): Map<String, Any> {
        // Confirm that transcription with that id exists and it belongs to employer
        val transcription = transcriptionRepository.findByIdAndUser(transcriptionId, employer).get()

        if (transcription == null) {
            return mapOf(
                "error" to true,
                "message" to "This analysis does not exist or does not belong to you."
            )
        }

        // Check if transcription already has an improvement request
        val improvementRequest = improvementRequestRepository.findByTranscription(transcription)

        if (improvementRequest != null) {
            return mapOf(
                "error" to true,
                "message" to "This analysis already has an active improvement request."
            )
        }
        
        // Process order
        val orderApproval = orderService.processImprovementRequest(employer, transcription, paymentIntent)

        if (orderApproval != null && orderApproval?.get("isApproved") as? Boolean == true) {
            return mapOf<String, Any>(
                "isApproved" to true,
                "improvementRequest" to (orderApproval.get("improvementRequest") as? Any ?: "Improvement request not found in order approval.")
            )
        }
        else {
            return mapOf(
                "error" to true,
                "message" to (orderApproval?.get("message") ?: "Order denied.")
            )
        }
    }

    override fun getImprovementRequestFromTranscription(transcription: AudioTranscription): ImprovementRequest? {
        return improvementRequestRepository.findByTranscription(transcription)
    }

    override fun getImprovementRequestFromTranscriptionIdAndEmployer(transcriptionId: UUID, employer: User): ImprovementRequest? {
        val transcription = transcriptionRepository.findByIdAndUser(transcriptionId, employer).get()
        
        if (transcription == null) {
            return null;
        }

        return improvementRequestRepository.findByTranscription(transcription)
    }

    override fun createImprovementResponseForEmployeeNot(employee: User): Map<String, Any>? {
        val employeesImprovementResponses = improvementResponseRepository.findAllByEmployeeOrderByTimestampUpdatedDesc(employee)

        if (!employeesImprovementResponses.isNullOrEmpty()) {
            val lastImprovementResponse = employeesImprovementResponses[0]

            if (lastImprovementResponse.status == ImprovementResponseStatusEnum.IN_PROGRESS) {
                return mapOf(
                    "error" to true,
                    "message" to "You are currently improving an analysis. Please finish it before requesting another one."
                )
            }

            val timeSinceLastImprovementResponse = Duration.between(lastImprovementResponse.timestampUpdated, Instant.now())

            if (timeSinceLastImprovementResponse.toHours() < 24) {
                val nextRequestTime = lastImprovementResponse.timestampUpdated.plus(Duration.ofHours(24))
                return mapOf(
                    "error" to true,
                    "message" to "You have already requested an analysis for improving within the last 24 hours. Please try again after $nextRequestTime."
                )
            }
        }

        val improvementRequests = improvementRequestRepository.findAllByEmployerNotAndStatus(employee, ImprovementRequestStatusEnum.AWAITING_WORKER)

        if (improvementRequests.isNullOrEmpty()) {
            return mapOf(
                "error" to true,
                "message" to "There are currently no new analyses for improving. Please try again later."
            )
        }

        var improvementRequest: ImprovementRequest? = null;
        val sortedImprovementRequests = improvementRequests.sortedByDescending { it.timestampCreated }

        for (i in 0 until sortedImprovementRequests.size) {
            val checkForAlreadyExistingImprovementResponse = improvementResponseRepository.findByImprovementRequestAndEmployee(sortedImprovementRequests[i], employee)

            if (checkForAlreadyExistingImprovementResponse == null) {
                improvementRequest = sortedImprovementRequests[i]
                break;
            }
        }

        if (improvementRequest == null) {
            return mapOf(
                "error" to true,
                "message" to "There are currently no new analyses for improving. Please try again later."
            )
        }

        // Create improvement response
        val improvementResponse = ImprovementResponse(
            improvementRequest = improvementRequest,
            employee = employee,
            oldTranscriptionText = improvementRequest.newTranscriptionText,
            newTranscriptionText = improvementRequest.newTranscriptionText,
            status = ImprovementResponseStatusEnum.IN_PROGRESS
        )

        improvementResponseRepository.save(improvementResponse)

        // Update improvement request status
        improvementRequest.status = ImprovementRequestStatusEnum.IN_PROGRESS
        
        improvementRequestRepository.save(improvementRequest)

        return mapOf(
            "success" to true
        )
    }

    override fun finishImprovementResponse(improvementResponse: ImprovementResponse): Map<String, Any>? {
        // Check improvementResponse validity and update it database
        val improvementResponseStatus: ImprovementResponseStatusEnum = validityCheckService.getFinishedImprovementFinalStatus(improvementResponse.newTranscriptionText, improvementResponse.oldTranscriptionText)

        improvementResponse.status = improvementResponseStatus

        improvementResponseRepository.save(improvementResponse)

        // Update improvementRequest in database
        var improvementRequest = improvementResponse.improvementRequest

        improvementRequest.improvedByCount = improvementRequest.improvedByCount+1
        improvementRequest.timestampUpdated = Instant.now()
        improvementRequest.newTranscriptionText = improvementResponse.newTranscriptionText

        if (improvementRequest.improvedByCount == 3) {
            improvementRequest.status = ImprovementRequestStatusEnum.FINISHED
        }
        else {
            improvementRequest.status = ImprovementRequestStatusEnum.AWAITING_WORKER
        }

        improvementRequestRepository.save(improvementRequest);

        // Update transcription in database
        var transcription = improvementRequest.transcription

        transcription.text = improvementResponse.newTranscriptionText
        transcription.timestampUpdated = Instant.now()

        transcriptionRepository.save(transcription);

        return mapOf(
            "success" to true,
        )
    }
}