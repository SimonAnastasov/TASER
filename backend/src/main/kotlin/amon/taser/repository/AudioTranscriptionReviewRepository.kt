package amon.taser.repository

import amon.taser.model.AudioTranscriptionReview
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import amon.taser.model.User
import amon.taser.model.AudioTranscription
import java.util.UUID

@Repository
interface AudioTranscriptionReviewRepository : JpaRepository<AudioTranscriptionReview, UUID> {
    fun findByAudioTranscription(audioTranscription: AudioTranscription): AudioTranscriptionReview?
}