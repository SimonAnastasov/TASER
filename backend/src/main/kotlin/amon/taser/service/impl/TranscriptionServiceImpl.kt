package amon.taser.service.impl

import amon.taser.model.User
import amon.taser.repository.TranscriptionRepository
import amon.taser.service.AudioTranscriptionApi
import amon.taser.service.TranscriptionService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class TranscriptionServiceImpl(
        val audioTranscriptionApi: AudioTranscriptionApi,
        val transcriptionRepository: TranscriptionRepository
): TranscriptionService {

    override fun startTranscription(file: MultipartFile, user: User?): UUID? {
        return audioTranscriptionApi.startTranscription(file, user)
    }

    override fun checkTranscriptionStatus(id: UUID): Boolean {
        return transcriptionRepository.findById(id).get().isCompleted
    }
    override fun getTranscriptionResult(id: UUID): String? {
        return transcriptionRepository.findById(id).get().text
    }

}