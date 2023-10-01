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
        @Column(length = 65535) var newTranscriptionText: String,

        @Enumerated(EnumType.STRING)
        var status: ImprovementRequestStatusEnum,

        var improvedByCount: Int = 0,

        @Id @GeneratedValue(strategy = GenerationType.UUID) val id: UUID? = null,
        
        @Column(name = "timestamp_created")
        val timestampCreated: Instant = Instant.now(),

        @Column(name = "timestamp_updated")
        var timestampUpdated: Instant = Instant.now()
)
