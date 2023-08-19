package amon.taser.web

import amon.taser.service.TranscriptionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


@RestController
class TranscriptionController(
        val transcriptionService: TranscriptionService
){
    @PostMapping("/upload")
    fun uploadAudioFile(@RequestParam("file") file: MultipartFile) : ResponseEntity<Any> {
        //send to service here
        val transcriptionUUID = transcriptionService.startTranscription(file)
        return ResponseEntity.ok(transcriptionUUID)
    }
}