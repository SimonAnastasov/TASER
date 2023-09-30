package amon.taser.service.impl

import amon.taser.service.ValidityCheckService
import org.springframework.stereotype.Service
import java.util.*


@Service
class ValidityCheckServiceImpl: ValidityCheckService {
    override fun checkReviewDifference(review: String, original: String): Boolean {
        val difference = jaccardIndex(review, original)
        return difference <= original.length * 0.25
    }
    fun jaccardIndex(a: String, b: String): Double {
        val aSet = a.split(" ").toSet()
        val bSet = b.split(" ").toSet()
        val intersection = aSet.intersect(bSet)
        val union = aSet.union(bSet)
        return intersection.size.toDouble() / union.size.toDouble()
    }

}