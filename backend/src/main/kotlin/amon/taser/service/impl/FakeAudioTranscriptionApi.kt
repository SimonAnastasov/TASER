package amon.taser.service.impl

import amon.taser.model.AudioTranscription
import amon.taser.repository.TranscriptionRepository
import amon.taser.service.AudioTranscriptionApi
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class FakeAudioTranscriptionApi (
        val audioTranscriptionRepository: TranscriptionRepository
): AudioTranscriptionApi {
    override fun startTranscription(audioFile: MultipartFile): UUID? {
        val audioTranscription = AudioTranscription(
                text = "This is a fake transcription",
                filename = audioFile.originalFilename!!,
                user = null,
                id = null
        )
        val aud = audioTranscriptionRepository.save(audioTranscription)
        aud.id?.let { transcribe(it) }
        return aud.id
    }

    private fun transcribe(audioFileID: UUID) {
        Thread.sleep(10000) // in non-fake implementation, this would be an async call to a transcription service
        val audioFile = audioTranscriptionRepository.findById(audioFileID).get()
        audioTranscriptionRepository.save(audioFile.copy(isCompleted = true))
    }
}