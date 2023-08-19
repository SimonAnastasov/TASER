package amon.taser.model

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name="taser_user")
data class User(
        val username: String,
        val password: String,
        val email: String,
        @Id @GeneratedValue(strategy = GenerationType.UUID) val id: UUID? = null
)
