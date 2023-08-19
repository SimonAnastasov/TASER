package amon.taser.service

import org.springframework.web.multipart.MultipartFile
import java.util.UUID

interface AudioTranscriptionApi {
    fun startTranscription(audioFile: MultipartFile): UUID?
}
