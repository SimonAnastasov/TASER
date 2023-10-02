package amon.taser.repository

import amon.taser.model.ImprovementRequest
import amon.taser.model.AudioTranscription
import amon.taser.model.enums.ImprovementRequestStatusEnum
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import amon.taser.model.User
import java.util.UUID

@Repository
interface ImprovementRequestRepository : JpaRepository<ImprovementRequest, UUID> {
    fun findByTranscription(transcription: AudioTranscription): ImprovementRequest?
    fun findAllByEmployerNotAndStatus(employer: User, status: ImprovementRequestStatusEnum): List<ImprovementRequest>?
    fun findAllByStatus(status: ImprovementRequestStatusEnum): List<ImprovementRequest>?
}