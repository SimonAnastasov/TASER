package amon.taser.repository

import amon.taser.model.ImprovementResponse
import amon.taser.model.ImprovementRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import amon.taser.model.User
import java.util.UUID

@Repository
interface ImprovementResponseRepository : JpaRepository<ImprovementResponse, UUID> {
    fun findAllByEmployeeOrderByTimestampUpdatedDesc(employee: User): List<ImprovementResponse>
    fun findByImprovementRequest(improvementRequest: ImprovementRequest): ImprovementResponse?
}