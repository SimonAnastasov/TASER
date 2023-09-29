package amon.taser.model

import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

@Entity
data class ImprovementRequest(
        @ManyToOne val transcription: AudioTranscription,
        @ManyToOne val employer: User,

        val oldTranscriptionText: String,
        val newTranscriptionText: String,
        val status: String,

        val improvedByCount: Int = 0,

        @Id @GeneratedValue(strategy = GenerationType.UUID) val id: UUID? = null,
        
        @Column(name = "timestamp_created")
        val timestampCreated: Instant = Instant.now(),

        @Column(name = "timestamp_updated")
        val timestampUpdated: Instant = Instant.now()
)
