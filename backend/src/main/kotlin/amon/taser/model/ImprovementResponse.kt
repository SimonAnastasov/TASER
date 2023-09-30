package amon.taser.model

import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

@Entity
data class ImprovementResponse(
        @ManyToOne val transcription: AudioTranscription,
        @ManyToOne val employee: User,

        val oldTranscriptionText: String,
        val newTranscriptionText: String,
        val status: String,

        @Id @GeneratedValue(strategy = GenerationType.UUID) val id: UUID? = null,
        
        @Column(name = "timestamp_created")
        val timestampCreated: Instant = Instant.now(),

        @Column(name = "timestamp_updated")
        val timestampUpdated: Instant = Instant.now()
)
