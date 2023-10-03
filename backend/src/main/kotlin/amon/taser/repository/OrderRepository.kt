package amon.taser.repository

import amon.taser.model.ImprovementRequest
import amon.taser.model.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import amon.taser.model.User
import java.util.UUID

@Repository
interface OrderRepository : JpaRepository<Order, UUID> {
    fun getOrderByImprovementRequest(request:ImprovementRequest): Order?
}