package amon.taser.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

@Entity
class AudioTranscriptionReview (
    @JsonIgnore
    @OneToOne val audioTranscription: AudioTranscription,

    @Column(length = 65535) var reviewText: String,

    var reviewRating: Float,
    
    @Id @GeneratedValue(strategy = GenerationType.UUID) val id: UUID? = null,

    @Column(name = "timestamp_created")
    val timestampCreated: Instant = Instant.now(),

    @Column(name = "timestamp_updated")
    var timestampUpdated: Instant = Instant.now()
) {
    fun copy(
            audioTranscription: AudioTranscription = this.audioTranscription,
            reviewText: String = this.reviewText,
            reviewRating: Float = this.reviewRating
    ): AudioTranscriptionReview {
        return AudioTranscriptionReview(audioTranscription, reviewText, reviewRating)
    }
}