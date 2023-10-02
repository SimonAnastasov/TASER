package amon.taser.model

import jakarta.persistence.*
import java.time.Instant
import java.util.UUID
import amon.taser.model.enums.ImprovementResponseStatusEnum

@Entity
data class ImprovementResponse(
        @ManyToOne val improvementRequest: ImprovementRequest,
        @ManyToOne val employee: User,

        @Column(length = 65535) val oldTranscriptionText: String,
        @Column(length = 65535) var newTranscriptionText: String,
        
        @Enumerated(EnumType.STRING)
        var status: ImprovementResponseStatusEnum,

        @Id @GeneratedValue(strategy = GenerationType.UUID) val id: UUID? = null,
        
        @Column(name = "timestamp_created")
        val timestampCreated: Instant = Instant.now(),

        @Column(name = "timestamp_updated")
        var timestampUpdated: Instant = Instant.now()
)
