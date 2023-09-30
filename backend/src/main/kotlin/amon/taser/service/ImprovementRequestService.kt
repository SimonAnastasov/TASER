package amon.taser.service

import amon.taser.model.User
import amon.taser.model.ImprovementRequest
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

interface ImprovementRequestService {
    fun requestImprovement(employee: User, transcriptionId: UUID): Map<String, Any>?
}