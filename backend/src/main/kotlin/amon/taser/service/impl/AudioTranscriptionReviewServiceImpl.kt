package amon.taser.service.impl

import amon.taser.model.User
import amon.taser.model.AudioTranscription
import amon.taser.model.AudioTranscriptionReview
import amon.taser.repository.AudioTranscriptionReviewRepository
import amon.taser.service.AudioTranscriptionReviewService
import amon.taser.service.TranscriptionService
import amon.taser.service.UserService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

import java.time.Instant

@Service
class AudioTranscriptionReviewServiceImpl (
        val audioTranscriptionReviewRepository: AudioTranscriptionReviewRepository,
        val transcriptionService: TranscriptionService,
        val userService: UserService
): AudioTranscriptionReviewService {

    override fun getAudioTranscriptionReviewFromAudioTranscriptionId(audioTranscriptionId: UUID): AudioTranscriptionReview? {
        val audioTranscription: AudioTranscription? = transcriptionService.getTranscriptionResult(audioTranscriptionId)

        if (audioTranscription == null) {
            return null;
        }

        return audioTranscriptionReviewRepository.findByAudioTranscription(audioTranscription)
    }

    override fun createAudioTranscriptionReview(audioTranscriptionId: UUID, reviewText: String, reviewRating: Float): AudioTranscriptionReview? {
        val audioTranscription: AudioTranscription? = transcriptionService.getTranscriptionResult(audioTranscriptionId)

        if (audioTranscription == null) {
            return null;
        }
        
        val audioTranscriptionReview = AudioTranscriptionReview(audioTranscription, reviewText, reviewRating)

        return audioTranscriptionReviewRepository.save(audioTranscriptionReview)
    }

    override fun updateAudioTranscriptionReview(id: UUID, reviewText: String, reviewRating: Float): AudioTranscriptionReview? {
        var audioTranscriptionReview = audioTranscriptionReviewRepository.findById(id).orElse(null)

        if (audioTranscriptionReview == null) {
            return null;
        }

        audioTranscriptionReview.reviewText = reviewText
        audioTranscriptionReview.reviewRating = reviewRating
        audioTranscriptionReview.timestampUpdated = Instant.now()

        return audioTranscriptionReviewRepository.save(audioTranscriptionReview)
    }

    override fun deleteAudioTranscriptionReview(id: UUID): Boolean {
    return try {
        audioTranscriptionReviewRepository.deleteById(id)
        true
    } catch (e: Exception) {
        false
    }
}
}
