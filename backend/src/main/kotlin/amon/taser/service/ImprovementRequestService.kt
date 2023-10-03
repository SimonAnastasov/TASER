package amon.taser.service

import amon.taser.model.User
import amon.taser.model.AudioTranscription
import amon.taser.model.ImprovementRequest
import amon.taser.model.ImprovementResponse
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

interface ImprovementRequestService {
    fun getImprovementRequestFromId(id: UUID): ImprovementRequest?

    fun requestImprovement(employer: User, transcriptionId: UUID, paymentIntent: Map<String, Any>): Map<String, Any>?

    fun getImprovementRequestFromTranscription(transcription: AudioTranscription): ImprovementRequest?
    fun getImprovementRequestFromTranscriptionIdAndEmployer(transcriptionId: UUID, employer: User): ImprovementRequest?
    fun createImprovementResponseForEmployeeNot(employee: User): Map<String, Any>?

    fun finishImprovementResponse(improvementResponse: ImprovementResponse): Map<String, Any>?
}