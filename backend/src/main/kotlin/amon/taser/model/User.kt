package amon.taser.model

import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

@Entity
@Table(name="taser_user")
data class User(
        val username: String,
        val password: String,
        val email: String,
        @Id @GeneratedValue(strategy = GenerationType.UUID) val id: UUID? = null,
        
        @Column(name = "timestamp_created", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP")
        val timestampCreated: Instant = Instant.now(),

        @Column(name = "timestamp_updated", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP")
        val timestampUpdated: Instant = Instant.now()
)
