package amon.taser.service.impl

import amon.taser.model.User
import amon.taser.model.Order
import amon.taser.model.AudioTranscription
import amon.taser.repository.TranscriptionRepository
import amon.taser.repository.OrderRepository
import amon.taser.service.OrderService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class OrderServiceImpl(
        val OrderRepository: OrderRepository
): OrderService {

    override fun processImprovementRequest(employer: User, transcription: AudioTranscription): Map<String, Any>? {
        // TODO: Replace with actual implementation

        return mapOf(
            "isApproved" to true
        )
    }
}