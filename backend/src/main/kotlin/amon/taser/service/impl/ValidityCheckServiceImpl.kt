package amon.taser.service.impl

import amon.taser.service.ValidityCheckService
import org.springframework.stereotype.Service
import kotlin.math.abs


@Service
class ValidityCheckServiceImpl: ValidityCheckService {
    override fun checkReviewDifference(review: String, original: String): Boolean {
        val similarity = jaccardIndex(review, original)
        return similarity >= 0.85 && abs(review.length - original.length) <= 500
    }
    fun jaccardIndex(a: String, b: String): Double {
        val aSet = a.split(" ").toSet()
        val bSet = b.split(" ").toSet()
        val intersection = aSet.intersect(bSet)
        val union = aSet.union(bSet)
        return intersection.size.toDouble() / union.size.toDouble()
    }

}