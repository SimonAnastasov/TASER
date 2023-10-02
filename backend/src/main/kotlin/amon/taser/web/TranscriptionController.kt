package amon.taser.web

import amon.taser.model.ResponseDto
import amon.taser.model.User
import amon.taser.config.filters.JWTAuthorizationFilter
import amon.taser.service.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.*

import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.multipart.MaxUploadSizeExceededException


@RestController
class TranscriptionController(
        val transcriptionService: TranscriptionService,
        val audioTranscriptionReviewService: AudioTranscriptionReviewService,
        val improvementRequestService: ImprovementRequestService,
        val userService: UserService,
        val storageService: StorageService,
        val filter: JWTAuthorizationFilter
){

    @RestControllerAdvice
    class GlobalExceptionHandler {
        @ExceptionHandler(MaxUploadSizeExceededException::class)
        fun handleMaxUploadSizeExceededException(ex: MaxUploadSizeExceededException): ResponseEntity<Any> {
            return ResponseEntity.ok(mapOf(
                "error" to true,
                "message" to "Maximum upload size exceeded"
            ))
        }
    }

    @PostMapping("/api/upload")
    fun uploadAudioFile(
        @RequestParam("file") file: MultipartFile,
        @RequestHeader("Authorization", required = false) authorizationHeader: String?
    ): ResponseEntity<Any> {

        val user: User? = filter.getUserFromAuthorizationHeader(authorizationHeader)
        storageService.storeFile(file)

        val transcriptionResult = transcriptionService.startTranscription(file, user)
        return ResponseEntity.ok(mapOf(
            "transcription" to transcriptionResult
        ))
    }

    @GetMapping("/api/history")
    fun getTranscriptionHistory(
        @RequestHeader("Authorization", required = false) authorizationHeader: String?
    ): ResponseEntity<Any> {

        val user: User? = filter.getUserFromAuthorizationHeader(authorizationHeader)

        if (user == null) {
            return ResponseEntity.ok(mapOf(
                "error" to true,
                "notLoggedIn" to true
            ))
        }

        val transcriptionHistory = transcriptionService.getTranscriptionsHistoryForUser(user)
        return ResponseEntity.ok(mapOf(
            "transcriptionHistory" to transcriptionHistory
        ))
    }

    @GetMapping("/api/transcription/{id}")
    fun getTranscription(
        @PathVariable id: UUID,
        @RequestHeader("Authorization", required = false) authorizationHeader: String?
    ): ResponseEntity<Any> {

        val user: User? = filter.getUserFromAuthorizationHeader(authorizationHeader)

        if (user == null) {
            return ResponseEntity.ok(mapOf(
                "error" to true,
                "notLoggedIn" to true
            ))
        }

        val transcription = transcriptionService.getTranscriptionResult(id)

        val transcriptionReview = audioTranscriptionReviewService.getAudioTranscriptionReviewFromAudioTranscriptionId(id)

        val transcriptionImprovementRequest = improvementRequestService.getImprovementRequestFromTranscriptionIdAndEmployer(id, user)

        return if (transcription != null) {
            ResponseEntity.ok(mapOf(
                "transcription" to transcription,
                "transcriptionReview" to transcriptionReview,
                "transcriptionImprovementInfo" to transcriptionImprovementRequest
            ))
        } else {
            ResponseEntity.ok(mapOf(
                "error" to true,
                "message" to "Analysis not found."
            ))
        }
    }

    @GetMapping("/api/checkResult")
    fun checkResult(@RequestParam("uuid") uuid: String) : ResponseEntity<ResponseDto> {
        val isComplete = transcriptionService.checkTranscriptionStatus(UUID.fromString(uuid))
        return if (isComplete){
            val result = transcriptionService.getTranscriptionResult(UUID.fromString(uuid))?.text ?: ""
            ResponseEntity.ok(ResponseDto(result, true))
        } else ResponseEntity.ok(ResponseDto(null, false))
    }
}