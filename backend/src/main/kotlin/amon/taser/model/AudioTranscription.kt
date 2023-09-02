package amon.taser.model

import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

@Entity
class AudioTranscription(
    @Column(length = 65535) val text: String,
    val filename: String,
    val isCompleted: Boolean = false,
    @ManyToOne val user: User?,
    @Id @GeneratedValue(strategy = GenerationType.UUID) val id: UUID?,

    @Column(name = "timestamp_created", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP")
    val timestampCreated: Instant = Instant.now(),

    @Column(name = "timestamp_updated", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP")
    val timestampUpdated: Instant = Instant.now()
) {
    fun copy(
            text: String = this.text,
            filename: String = this.filename,
            isCompleted: Boolean = this.isCompleted,
            user: User? = this.user,
            id: UUID? = this.id
    ): AudioTranscription {
        return AudioTranscription(text, filename, isCompleted,user, id)
    }
}