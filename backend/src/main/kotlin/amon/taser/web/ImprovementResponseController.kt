package amon.taser.web

import amon.taser.model.ResponseDto
import amon.taser.model.User
import amon.taser.config.filters.JWTAuthorizationFilter
import amon.taser.service.TranscriptionService
import amon.taser.service.ImprovementRequestService
import amon.taser.service.ImprovementResponseService
import amon.taser.repository.ImprovementResponseRepository
import amon.taser.service.AudioTranscriptionReviewService
import amon.taser.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.*
import java.time.Instant

import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.multipart.MaxUploadSizeExceededException


@RestController
class ImprovementResponseController(
        val transcriptionService: TranscriptionService,
        val improvementRequestService: ImprovementRequestService,
        val improvementResponseService: ImprovementResponseService,
        val improvementResponseRepository: ImprovementResponseRepository,
        val userService: UserService,
        filter: JWTAuthorizationFilter
){

    private val filter: JWTAuthorizationFilter

    init {
        this.filter = filter
    }

    @GetMapping("/api/improvements/getPaidHistory")
    fun getPaidHistory(
        @RequestHeader("Authorization", required = false) authorizationHeader: String?
    ): ResponseEntity<Any> {

        val user: User? = filter.getUserFromAuthorizationHeader(authorizationHeader)

        if (user == null) {
            return ResponseEntity.ok(mapOf(
                "error" to true,
                "notLoggedIn" to true
            ))
        }

        val improvementResponsesHistory = improvementResponseService.getImprovementsHistoryForEmployee(user)

        return if (improvementResponsesHistory != null) { 
            ResponseEntity.ok(mapOf(
                "improvementsHistory" to improvementResponsesHistory
            ))
        } else {
            ResponseEntity.ok(mapOf(
                "error" to true,
                "message" to "History or active analyses for improving not found."
            ))
        }
    }

    @GetMapping("/api/improvements/requestAnalysisForImproving")
    fun requestAnalysisForImproving(
        @RequestHeader("Authorization", required = false) authorizationHeader: String?
    ): ResponseEntity<Any> {

        val user: User? = filter.getUserFromAuthorizationHeader(authorizationHeader)

        if (user == null) {
            return ResponseEntity.ok(mapOf(
                "error" to true,
                "notLoggedIn" to true
            ))
        }

        val requestAnalysisForImproving = improvementRequestService.createImprovementResponseForEmployeeNot(user)

        if (requestAnalysisForImproving?.get("error") as? Boolean == true) {
            return ResponseEntity.ok(mapOf(
                "error" to true,
                "message" to requestAnalysisForImproving?.get("message")
            ))
        }

        val improvementResponsesHistory = improvementResponseService.getImprovementsHistoryForEmployee(user)

        return if (improvementResponsesHistory != null) { 
            ResponseEntity.ok(mapOf(
                "improvementsHistory" to improvementResponsesHistory
            ))
        } else {
            ResponseEntity.ok(mapOf(
                "error" to true,
                "message" to "Could not request an analysis for improving at this time. Please try again later."
            ))
        }
    }

    @GetMapping("/api/improvements/enterIsImprovingMode/{improvementResponseId}")
    fun enterIsImprovingMode(
        @PathVariable improvementResponseId: UUID,
        @RequestHeader("Authorization", required = false) authorizationHeader: String?,
    ): ResponseEntity<Any> {
        val user: User? = filter.getUserFromAuthorizationHeader(authorizationHeader)

        if (user == null) {
            return ResponseEntity.ok(mapOf(
                "error" to true,
                "notLoggedIn" to true
            ))
        }

        var improvementResponse = improvementResponseService.getImprovementResponseFromId(improvementResponseId)

        if (improvementResponse == null) {
            return ResponseEntity.ok(mapOf(
                "error" to true,
                "message" to "Could not find analysis for improvement."
            ))
        }
        else if (improvementResponse.employee != user) {
            return ResponseEntity.ok(mapOf(
                "error" to true,
                "message" to "This analysis for improvement is not yours."
            ))
        }

        improvementResponse.improvementRequest.transcription.text = improvementResponse.newTranscriptionText

        return ResponseEntity.ok(mapOf(
            "improvementResponse" to improvementResponse
        ))
    }

    @PostMapping("/api/improvements/syncImprovementResponse/{improvementResponseId}")
    fun enterIsImprovingMode(
        @PathVariable improvementResponseId: UUID,
        @RequestBody body: Map<String, String>,
        @RequestHeader("Authorization", required = false) authorizationHeader: String?,
    ): ResponseEntity<Any> {
        body.forEach { (key, value) ->
            System.out.println("$key: $value")
        }

        val user: User? = filter.getUserFromAuthorizationHeader(authorizationHeader)

        if (user == null) {
            return ResponseEntity.ok(mapOf(
                "error" to true,
                "notLoggedIn" to true
            ))
        }

        var improvementResponse = improvementResponseService.getImprovementResponseFromId(improvementResponseId)

        if (improvementResponse == null) {
            return ResponseEntity.ok(mapOf(
                "error" to true,
                "message" to "Could not find analysis for improving."
            ))
        }
        else if (improvementResponse.employee != user) {
            return ResponseEntity.ok(mapOf(
                "error" to true,
                "message" to "This analysis for improving is not yours."
            ))
        }

        if (body?.get("newTranscriptionText") == null) {
            return ResponseEntity.ok(mapOf(
                "error" to true,
                "message" to "You've sent empty text for this analysis."
            ))
        }

        improvementResponse.newTranscriptionText = body?.get("newTranscriptionText") ?: ""
        improvementResponse.timestampUpdated = Instant.now();

        improvementResponseRepository.save(improvementResponse);

        return ResponseEntity.ok(mapOf(
            "success" to true
        ))
    }
}