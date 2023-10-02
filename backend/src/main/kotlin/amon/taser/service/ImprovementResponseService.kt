package amon.taser.service

import amon.taser.model.User
import amon.taser.model.ImprovementResponse
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

interface ImprovementResponseService {
    fun getImprovementsHistoryForEmployee(employee: User): List<ImprovementResponse>
    fun getImprovementResponseFromId(id: UUID): ImprovementResponse?
}