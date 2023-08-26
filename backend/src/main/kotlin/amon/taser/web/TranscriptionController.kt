package amon.taser.web

import amon.taser.model.ResponseDto
import amon.taser.service.TranscriptionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.*


@RestController
class TranscriptionController(
        val transcriptionService: TranscriptionService
){
    @PostMapping("/api/upload")
    fun uploadAudioFile(@RequestParam("file") file: MultipartFile) : ResponseEntity<Any> {
        //send to service here
        val transcriptionUUID = transcriptionService.startTranscription(file)
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