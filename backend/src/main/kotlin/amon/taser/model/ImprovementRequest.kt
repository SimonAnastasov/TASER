package amon.taser.model

import jakarta.persistence.*
import java.time.Instant
import java.util.UUID
import amon.taser.model.enums.ImprovementRequestStatusEnum

@Entity
data class ImprovementRequest(
        @ManyToOne val transcription: AudioTranscription,
        @ManyToOne val employer: User,

        @Column(length = 65535) val oldTranscriptionText: String,
        @Column(length = 65535) val newTranscriptionText: String,

        @Enumerated(EnumType.STRING)
        val status: ImprovementRequestStatusEnum,

        val improvedByCount: Int = 0,

        @Id @GeneratedValue(strategy = GenerationType.UUID) val id: UUID? = null,
        
        @Column(name = "timestamp_created")
        val timestampCreated: Instant = Instant.now(),

        @Column(name = "timestamp_updated")
        val timestampUpdated: Instant = Instant.now()
)
