package amon.taser.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

@Entity
class AudioTranscriptionReview (
    @Column(length = 65535) val reviewText: String,

    val reviewRating: Float,
    
    @OneToOne val audioTranscription: AudioTranscription,
    
    @Id @GeneratedValue(strategy = GenerationType.UUID) val id: UUID?,

    @Column(name = "timestamp_created")
    val timestampCreated: Instant = Instant.now(),

    @Column(name = "timestamp_updated")
    val timestampUpdated: Instant = Instant.now()
) {
    fun copy(
            reviewText: String = this.reviewText,
            reviewRating: Float = this.reviewRating,
            audioTranscription: AudioTranscription = this.audioTranscription,
            id: UUID? = this.id
    ): AudioTranscriptionReview {
        return AudioTranscriptionReview(reviewText, reviewRating, audioTranscription, id)
    }
}