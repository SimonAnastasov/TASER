package amon.taser.service

interface ValidityCheckService {
    fun checkReviewDifference(review: String, original: String): Boolean
}