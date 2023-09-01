package amon.taser.service

import amon.taser.model.User
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

interface TranscriptionService {
    fun startTranscription(file: MultipartFile, user: User?): Map<String, String>
    fun checkTranscriptionStatus(id: UUID) : Boolean
    fun getTranscriptionResult(id: UUID): String?
}