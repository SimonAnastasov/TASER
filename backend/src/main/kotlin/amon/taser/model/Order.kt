package amon.taser.model

import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

import amon.taser.model.enums.OrderStatusEnum

@Entity
@Table(name="taser_order")
data class Order(
        @ManyToOne val improvementRequest: ImprovementRequest,
        @ManyToOne val user: User,

        val amountInDollars: Float,
        var status: OrderStatusEnum,

        @Id @GeneratedValue(strategy = GenerationType.UUID) val id: UUID? = null,
        
        @Column(name = "timestamp_created")
        val timestampCreated: Instant = Instant.now(),

        @Column(name = "timestamp_updated")
        var timestampUpdated: Instant = Instant.now()
)
