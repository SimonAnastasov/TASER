package amon.taser.repository

import amon.taser.model.ImprovementRequest
import amon.taser.model.AudioTranscription
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import amon.taser.model.User
import java.util.UUID

@Repository
interface ImprovementRequestRepository : JpaRepository<ImprovementRequest, UUID> {
    fun findByTranscription(transcription: AudioTranscription): ImprovementRequest?
}