package amon.taser.service

import amon.taser.model.enums.ImprovementResponseStatusEnum

interface ValidityCheckService {
    fun getFinishedImprovementFinalStatus(improvement: String, original: String): ImprovementResponseStatusEnum
    fun checkReviewDifference(review: String, original: String): Boolean
}