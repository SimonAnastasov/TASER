package amon.taser.service

import amon.taser.model.User
import amon.taser.model.Order
import amon.taser.model.AudioTranscription
import amon.taser.model.ImprovementRequest
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

interface OrderService {
    fun processImprovementRequest(employer: User, transcription: AudioTranscription, paymentIntent: Map<String, Any>): Map<String, Any>?
    fun getOrderByRequest(request: ImprovementRequest): Order?
}