package amon.taser.service

import amon.taser.model.User
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

interface AudioTranscriptionApi {
    fun startTranscription(audioFile: MultipartFile, user: User?): UUID?
}
