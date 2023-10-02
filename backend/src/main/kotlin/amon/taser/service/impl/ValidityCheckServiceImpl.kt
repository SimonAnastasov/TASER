package amon.taser.service.impl

import amon.taser.service.ValidityCheckService
import org.springframework.stereotype.Service
import kotlin.math.abs

import amon.taser.model.enums.ImprovementResponseStatusEnum

@Service
class ValidityCheckServiceImpl: ValidityCheckService {
    override fun getFinishedImprovementFinalStatus(improvement: String, original: String): ImprovementResponseStatusEnum {
        val similarity = jaccardIndex(improvement, original)
        val lengthDifference = abs(improvement.length - original.length)

        System.out.println("Similarity--------------------")
        System.out.println(similarity)
        System.out.println("Length Difference--------------------")
        System.out.println(lengthDifference)
    
        if (similarity >= 0.85 && lengthDifference <= 500) {
            return ImprovementResponseStatusEnum.FINISHED
        } else if (similarity >= 0.5 && lengthDifference <= 500) {
            return ImprovementResponseStatusEnum.INADEQUATE
        } else {
            return ImprovementResponseStatusEnum.MALICIOUS
        }
    }
    
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