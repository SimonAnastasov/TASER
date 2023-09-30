package amon.taser.service

import org.springframework.web.multipart.MultipartFile
import java.io.File

interface StorageService {
    fun storeFile(file: MultipartFile) : String
    fun loadFileAsResource(filename: String) : File



}