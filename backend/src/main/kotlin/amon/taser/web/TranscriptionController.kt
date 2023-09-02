package amon.taser.web

import amon.taser.model.ResponseDto
import amon.taser.model.User
import amon.taser.config.filters.JWTAuthorizationFilter
import amon.taser.service.TranscriptionService
import amon.taser.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.*

import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.multipart.MaxUploadSizeExceededException


@RestController
class TranscriptionController(
        val transcriptionService: TranscriptionService,
        val userService: UserService,
        filter: JWTAuthorizationFilter
){

    private val filter: JWTAuthorizationFilter

    init {
        this.filter = filter
    }
    
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
        var user: User? = filter.getUserFromAuthorizationHeader(authorizationHeader)
        
        val transcriptionResult = transcriptionService.startTranscription(file, user)
        return ResponseEntity.ok(mapOf(
            "audioFileId" to transcriptionResult["id"],
            "transcription" to transcriptionResult["text"]
        ))
    }

    @GetMapping("/api/checkResult")
    fun checkResult(@RequestParam("uuid") uuid: String) : ResponseEntity<ResponseDto> {
        val isComplete = transcriptionService.checkTranscriptionStatus(UUID.fromString(uuid))
        return if (isComplete){
            val result = transcriptionService.getTranscriptionResult(UUID.fromString(uuid)) ?:""
            ResponseEntity.ok(ResponseDto(result, true))
        } else ResponseEntity.ok(ResponseDto(null, false))
    }
}