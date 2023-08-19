package amon.taser.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.util.UUID

@Entity
class AudioTranscription(
        val text: String,
        val filename: String,
        val isCompleted: Boolean = false,
        @ManyToOne val user: User?,
        @Id @GeneratedValue(strategy = GenerationType.UUID) val id: UUID?
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