package amon.taser.service

import org.springframework.web.multipart.MultipartFile
import java.util.UUID

interface TranscriptionService {
    fun startTranscription(file: MultipartFile) : UUID?
    fun checkTranscriptionStatus(id: UUID) : Boolean
}