package amon.taser.service

import amon.taser.model.User
import amon.taser.model.Order
import amon.taser.model.AudioTranscription
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

interface OrderService {
    fun processImprovementRequest(employer: User, transcription: AudioTranscription): Map<String, Any>?
}