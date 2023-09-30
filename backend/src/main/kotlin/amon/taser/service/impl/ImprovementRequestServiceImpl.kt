package amon.taser.service.impl

import amon.taser.model.User
import amon.taser.model.AudioTranscription
import amon.taser.model.ImprovementRequest
import amon.taser.model.enums.ImprovementRequestStatusEnum
import amon.taser.repository.TranscriptionRepository
import amon.taser.repository.ImprovementRequestRepository
import amon.taser.service.ImprovementRequestService
import amon.taser.service.OrderService
import amon.taser.service.TranscriptionService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class ImprovementRequestServiceImpl(
        val improvementRequestRepository: ImprovementRequestRepository,
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
                status = ImprovementRequestStatusEnum.IN_PROGRESS
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
}