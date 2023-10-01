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
import amon.taser.service.OrderService
import amon.taser.service.TranscriptionService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class ImprovementRequestServiceImpl(
        val improvementRequestRepository: ImprovementRequestRepository,
        val improvementResponseRepository: ImprovementResponseRepository,
        val orderService: OrderService,
        val transcriptionService: TranscriptionService,
        val transcriptionRepository: TranscriptionRepository,
): ImprovementRequestService {

    override fun requestImprovement(employer: User, transcriptionId: UUID): Map<String, Any>? {
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
        val orderApproval = orderService.processImprovementRequest(employer, transcription)

        if (orderApproval != null && orderApproval?.get("isApproved") as? Boolean == true) {
            // Create improvement request
            val improvementRequest = ImprovementRequest(
                transcription = transcription,
                employer = employer,
                oldTranscriptionText = transcription.text,
                newTranscriptionText = transcription.text,
                status = ImprovementRequestStatusEnum.AWAITING_WORKER
            )

            improvementRequestRepository.save(improvementRequest)

            return mapOf(
                "isApproved" to true,
                "improvementRequest" to improvementRequest
            )
        }
        else {
            return mapOf(
                "error" to true,
                "message" to (orderApproval?.get("message") ?: "Order denied.")
            )
        }

        return null;
    }

    override fun getImprovementRequestFromTranscriptionIdAndEmployer(transcriptionId: UUID, employer: User): ImprovementRequest? {
        val transcription = transcriptionRepository.findByIdAndUser(transcriptionId, employer).get()
        
        if (transcription == null) {
            return null;
        }

        return improvementRequestRepository.findByTranscription(transcription)
    }

    override fun createImprovementResponseForEmployeeNot(employee: User): Map<String, Any>? {
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
            val checkForAlreadyExistingImprovementResponse = improvementResponseRepository.findByImprovementRequest(sortedImprovementRequests[i])

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
            oldTranscriptionText = improvementRequest.oldTranscriptionText,
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
}