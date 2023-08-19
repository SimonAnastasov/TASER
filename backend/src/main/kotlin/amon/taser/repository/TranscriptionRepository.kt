package amon.taser.repository

import amon.taser.model.AudioTranscription
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TranscriptionRepository : JpaRepository<AudioTranscription, UUID> {
}