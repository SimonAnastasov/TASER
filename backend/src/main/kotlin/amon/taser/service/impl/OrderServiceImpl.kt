package amon.taser.service.impl

import amon.taser.model.User
import amon.taser.model.Order
import amon.taser.model.ImprovementRequest
import amon.taser.model.enums.OrderStatusEnum
import amon.taser.model.enums.ImprovementRequestStatusEnum
import amon.taser.model.AudioTranscription
import amon.taser.repository.TranscriptionRepository
import amon.taser.repository.OrderRepository
import amon.taser.repository.ImprovementRequestRepository
import amon.taser.service.OrderService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class OrderServiceImpl(
        val orderRepository: OrderRepository,
        val improvementRequestRepository: ImprovementRequestRepository
): OrderService {

    override fun processImprovementRequest(employer: User, transcription: AudioTranscription, paymentIntent: Map<String, Any>): Map<String, Any>? {
        // Create improvement request
        val improvementRequest = ImprovementRequest(
            transcription = transcription,
            employer = employer,
            oldTranscriptionText = transcription.text,
            newTranscriptionText = transcription.text,
            status = ImprovementRequestStatusEnum.AWAITING_WORKER
        )

        improvementRequestRepository.save(improvementRequest)
        
        // Create order
        val order = Order(
            improvementRequest = improvementRequest,
            user = employer,
            amountInDollars = 4.17.toDouble().toFloat(),
            status = OrderStatusEnum.FULFILLED
        )

        orderRepository.save(order)

        return mapOf(
            "isApproved" to true,
            "improvementRequest" to improvementRequest
        )
    }
}