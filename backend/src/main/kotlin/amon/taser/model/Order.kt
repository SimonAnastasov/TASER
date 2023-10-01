package amon.taser.model

import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

@Entity
@Table(name="taser_order")
data class Order(
        @ManyToOne val transcription: AudioTranscription,
        @ManyToOne val user: User,

        val amountInDollars: Float,
        var status: String,

        @Id @GeneratedValue(strategy = GenerationType.UUID) val id: UUID? = null,
        
        @Column(name = "timestamp_created")
        val timestampCreated: Instant = Instant.now(),

        @Column(name = "timestamp_updated")
        var timestampUpdated: Instant = Instant.now()
)
