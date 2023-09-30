package amon.taser.web

import amon.taser.model.ResponseDto
import amon.taser.model.User
import amon.taser.config.filters.JWTAuthorizationFilter
import amon.taser.service.TranscriptionService
// import amon.taser.service.ImprovementRequestService
import amon.taser.service.ImprovementResponseService
import amon.taser.service.AudioTranscriptionReviewService
import amon.taser.service.UserService
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
class ImprovementResponseController(
        val transcriptionService: TranscriptionService,
        // val improvementRequestService: ImprovementRequestService,
        val improvementResponseService: ImprovementResponseService,
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
        return ResponseEntity.ok(mapOf(
            "improvementsHistory" to improvementResponsesHistory
        ))
    }
}