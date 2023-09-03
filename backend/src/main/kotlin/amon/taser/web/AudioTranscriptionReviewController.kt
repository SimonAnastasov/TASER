package amon.taser.web

import amon.taser.model.ResponseDto
import amon.taser.model.AudioTranscriptionReviewDto
import amon.taser.model.User
import amon.taser.config.filters.JWTAuthorizationFilter
import amon.taser.service.TranscriptionService
import amon.taser.service.AudioTranscriptionReviewService
import amon.taser.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.*

import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.multipart.MaxUploadSizeExceededException


@RestController
class AudioTranscriptionReviewController (
        val audioTranscriptionReviewService: AudioTranscriptionReviewService,
        filter: JWTAuthorizationFilter
) {

    private val filter: JWTAuthorizationFilter

    init {
        this.filter = filter
    }

    @GetMapping("/api/transcription/{audioTranscriptionId}/review")
    fun getAudioTranscriptionReviewFromAudioTranscriptionId(
        @PathVariable("audioTranscriptionId") audioTranscriptionId: UUID,
        @RequestHeader("Authorization", required = false) authorizationHeader: String?
    ): ResponseEntity<Any> {

        val user: User? = filter.getUserFromAuthorizationHeader(authorizationHeader)

        if (user == null) {
            return ResponseEntity.ok(mapOf(
                "error" to true,
                "notLoggedIn" to true
            ))
        }

        val audioTranscriptionReview = audioTranscriptionReviewService.getAudioTranscriptionReviewFromAudioTranscriptionId(audioTranscriptionId)
        
        return if (audioTranscriptionReview != null) {
            ResponseEntity.ok(mapOf(
                "review" to audioTranscriptionReview
            ))
        } else {
            ResponseEntity.ok(mapOf(
                "error" to true,
                "notFound" to true,
            ))
        }
    }

    @PostMapping("/api/transcription/{audioTranscriptionId}/review")
    fun createAudioTranscriptionReview(
        @PathVariable("audioTranscriptionId") audioTranscriptionId: UUID,
        @RequestHeader("Authorization", required = false) authorizationHeader: String?,
        @RequestBody audioTranscriptionReviewDto: AudioTranscriptionReviewDto
    ): ResponseEntity<Any> {

        val user: User? = filter.getUserFromAuthorizationHeader(authorizationHeader)

        if (user == null) {
            return ResponseEntity.ok(mapOf(
                "error" to true,
                "notLoggedIn" to true
            ))
        }

        val audioTranscriptionReview = audioTranscriptionReviewService.createAudioTranscriptionReview(audioTranscriptionId, audioTranscriptionReviewDto.reviewText, audioTranscriptionReviewDto.reviewRating)

        return if (audioTranscriptionReview != null) {
            ResponseEntity.ok(mapOf(
                "review" to audioTranscriptionReview
            ))
        } else {
            ResponseEntity.ok(mapOf(
                "error" to true,
                "message" to "Unknown error."
            ))
        }
    }

    @PutMapping("/api/review/{id}")
    fun updateAudioTranscriptionReview(
        @PathVariable("id") id: UUID,
        @RequestHeader("Authorization", required = false) authorizationHeader: String?,
        @RequestBody audioTranscriptionReviewDto: AudioTranscriptionReviewDto
    ): ResponseEntity<Any> {

        val user: User? = filter.getUserFromAuthorizationHeader(authorizationHeader)

        if (user == null) {
            return ResponseEntity.ok(mapOf(
                "error" to true,
                "notLoggedIn" to true
            ))
        }

        val audioTranscriptionReview = audioTranscriptionReviewService.updateAudioTranscriptionReview(id, audioTranscriptionReviewDto.reviewText, audioTranscriptionReviewDto.reviewRating)

        return if (audioTranscriptionReview != null) {
            ResponseEntity.ok(mapOf(
                "review" to audioTranscriptionReview
            ))
        } else {
            ResponseEntity.ok(mapOf(
                "error" to true,
                "message" to "Unknown error."
            ))
        }
    }

    @DeleteMapping("/api/review/{id}")
    fun deleteAudioTranscriptionReview(
        @PathVariable("id") id: UUID,
        @RequestHeader("Authorization", required = false) authorizationHeader: String?,
    ): ResponseEntity<Any> {

        val user: User? = filter.getUserFromAuthorizationHeader(authorizationHeader)

        if (user == null) {
            return ResponseEntity.ok(mapOf(
                "error" to true,
                "notLoggedIn" to true
            ))
        }

        val isDeleted = audioTranscriptionReviewService.deleteAudioTranscriptionReview(id)

        return if (isDeleted) {
            ResponseEntity.ok(mapOf(
                "isDeleted" to true
            ))
        } else {
            ResponseEntity.ok(mapOf(
                "error" to true,
                "message" to "Unknown error."
            ))
        }
    }
}