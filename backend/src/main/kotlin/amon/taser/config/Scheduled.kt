package amon.taser.config

import amon.taser.model.enums.ImprovementRequestStatusEnum
import amon.taser.repository.ImprovementRequestRepository
import amon.taser.repository.ImprovementResponseRepository
import amon.taser.service.StripeService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class Scheduled (
    val stripeService: StripeService,
    val improvementRequestRepository: ImprovementRequestRepository,
    val improvementResponseRepository: ImprovementResponseRepository
) {
    @Scheduled(cron= "0 6 * * *")
    fun payOutToUsers() {
        val requests = improvementRequestRepository.findAllByStatus(ImprovementRequestStatusEnum.FINISHED)
        // TODO : get amount from order
        requests?.forEach {request ->
            val responses = improvementResponseRepository.findAllByImprovementRequest(request)
            responses?.forEach {
                it.employee.connectedAccountId?.let { it1 -> stripeService.payOutToUser(it1, 5) }
            }
        }
    }
}