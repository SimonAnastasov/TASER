package amon.taser.service.impl

import amon.taser.service.StorageService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Service
class StorageServiceImpl: StorageService {

    private val fileStorageLocation: Path = Paths.get("backend/uploads").toAbsolutePath().normalize()
    override fun storeFile(file: MultipartFile): String {
        if (file.isEmpty) {
            throw Exception("Failed to store empty file")
        }
        if (!Files.exists(fileStorageLocation)) {
            Files.createDirectories(fileStorageLocation)
        }
        val destinationPath = Paths.get(fileStorageLocation.toString() + "/" + file.originalFilename)
        try {
            Files.copy(file.inputStream, destinationPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING)
        } catch (e: Exception) {
            throw Exception("Failed to store file", e)
        }
        return file.originalFilename?:""
    }

    override fun loadFileAsResource(filename: String): File {
        val filePath = Paths.get("$fileStorageLocation/$filename")
        return filePath.toFile()
    }
}