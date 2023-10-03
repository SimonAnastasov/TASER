package amon.taser.config

import amon.taser.model.enums.ImprovementRequestStatusEnum
import amon.taser.repository.ImprovementRequestRepository
import amon.taser.repository.ImprovementResponseRepository
import amon.taser.service.OrderService
import amon.taser.service.StripeService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class Scheduled (
    val stripeService: StripeService,
    val improvementRequestRepository: ImprovementRequestRepository,
    val improvementResponseRepository: ImprovementResponseRepository,
    val orderService: OrderService
) {
    @Scheduled(cron= "0 0 6 * * *")
    fun payOutToUsers() {
        val requests = improvementRequestRepository.findAllByStatus(ImprovementRequestStatusEnum.FINISHED)
        requests?.forEach {request ->
            val order = orderService.getOrderByRequest(request)
            val responses = improvementResponseRepository.findAllByImprovementRequest(request)
            val amountEach = responses?.let { order?.amountInDollars?.div(it.size) ?: 0.0f }
            responses?.forEach {
                it.employee.connectedAccountId?.let { it1 -> stripeService.payOutToUser(it1,
                    amountEach?.toInt()?.times(100) ?: 0
                ) }
            }
            request.status = ImprovementRequestStatusEnum.PAID
            improvementRequestRepository.save(request)
        }
    }
}