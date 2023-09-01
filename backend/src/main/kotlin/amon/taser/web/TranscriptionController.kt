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

    @PostMapping("/api/upload")
    fun uploadAudioFile(
        @RequestParam("file") file: MultipartFile,
        @RequestHeader("Authorization", required = false) authorizationHeader: String?
    ): ResponseEntity<Any> {
        var user: User? = null

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            // Unauthorized
        }
        else {
            val token = authorizationHeader.substring(7)
            
            val authorizationClaims = filter.getToken(token)
            if (authorizationClaims != null) {
                val username: String = authorizationClaims.getName()
                user = userService.getUserByUsername(username)
            }
        }
        
        val transcriptionUUID = transcriptionService.startTranscription(file, user)
        return ResponseEntity.ok(transcriptionUUID)
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