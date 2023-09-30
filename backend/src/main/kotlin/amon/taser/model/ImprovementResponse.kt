package amon.taser.model

import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

@Entity
data class ImprovementResponse(
        @ManyToOne val transcription: AudioTranscription,
        @ManyToOne val employee: User,

        @Column(length = 65535) val oldTranscriptionText: String,
        @Column(length = 65535) val newTranscriptionText: String,
        val status: String,

        @Id @GeneratedValue(strategy = GenerationType.UUID) val id: UUID? = null,
        
        @Column(name = "timestamp_created")
        val timestampCreated: Instant = Instant.now(),

        @Column(name = "timestamp_updated")
        val timestampUpdated: Instant = Instant.now()
)
