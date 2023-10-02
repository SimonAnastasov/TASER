package amon.taser.service.impl

import amon.taser.model.User
import amon.taser.model.ImprovementResponse
import amon.taser.repository.TranscriptionRepository
import amon.taser.repository.ImprovementResponseRepository
import amon.taser.service.ImprovementResponseService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class ImprovementResponseServiceImpl(
        val improvementResponseRepository: ImprovementResponseRepository,
        val transcriptionRepository: TranscriptionRepository
): ImprovementResponseService {

    override fun getImprovementsHistoryForEmployee(employee: User): List<ImprovementResponse> {
        return improvementResponseRepository.findAllByEmployeeOrderByTimestampUpdatedDesc(employee)
    }

    override fun getImprovementResponseFromId(id: UUID): ImprovementResponse? {
        return improvementResponseRepository.findById(id).get()
    }
}