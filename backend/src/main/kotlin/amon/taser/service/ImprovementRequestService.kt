package amon.taser.service

import amon.taser.model.User
import amon.taser.model.AudioTranscription
import amon.taser.model.ImprovementRequest
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

interface ImprovementRequestService {
    fun requestImprovement(employer: User, transcriptionId: UUID): Map<String, Any>?
    fun getImprovementRequestFromTranscriptionIdAndEmployer(transcriptionId: UUID, employer: User): ImprovementRequest?
}