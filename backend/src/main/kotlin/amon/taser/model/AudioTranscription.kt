package amon.taser.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

@Entity
class AudioTranscription(
    @Column(length = 65535) var text: String,

    val filename: String,
    
    var isCompleted: Boolean = false,

    @JsonIgnore
    @ManyToOne val user: User?,
    
    @Id @GeneratedValue(strategy = GenerationType.UUID) val id: UUID?,

    @Column(name = "timestamp_created")
    val timestampCreated: Instant = Instant.now(),

    @Column(name = "timestamp_updated")
    var timestampUpdated: Instant = Instant.now()
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