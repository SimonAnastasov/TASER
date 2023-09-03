package amon.taser.service

import amon.taser.model.User
import amon.taser.model.AudioTranscription
import amon.taser.model.AudioTranscriptionReview
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

interface AudioTranscriptionReviewService {
    fun getAudioTranscriptionReviewFromAudioTranscriptionId(audioTranscriptionId: UUID): AudioTranscriptionReview?
    fun createAudioTranscriptionReview(audioTranscriptionId: UUID, reviewText: String, reviewRating: Float): AudioTranscriptionReview?
    fun updateAudioTranscriptionReview(id: UUID, reviewText: String, reviewRating: Float): AudioTranscriptionReview?
    fun deleteAudioTranscriptionReview(id: UUID): Boolean
}