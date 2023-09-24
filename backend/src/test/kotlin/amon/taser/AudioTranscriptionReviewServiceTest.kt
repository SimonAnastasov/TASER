import amon.taser.model.AudioTranscription
import amon.taser.model.AudioTranscriptionReview
import amon.taser.model.User
import amon.taser.repository.AudioTranscriptionReviewRepository
import amon.taser.service.TranscriptionService
import amon.taser.service.UserService
import amon.taser.service.impl.AudioTranscriptionReviewServiceImpl
import amon.taser.service.impl.output
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile
import java.util.*

@ExtendWith(MockitoExtension::class)
class AudioTranscriptionReviewServiceImplTest {

    private lateinit var audioTranscriptionReviewRepository: AudioTranscriptionReviewRepository
    private lateinit var transcriptionService: TranscriptionService
    private lateinit var userService: UserService
    private lateinit var audioTranscriptionReviewService: AudioTranscriptionReviewServiceImpl

    private val user = User("testUser", "password", "testUser")
    private val fileName = "dummy-file.txt"
    private val contentType = "text/plain"
    private val content = "This is the file content.".toByteArray()

    private val file: MultipartFile = MockMultipartFile(fileName, fileName, contentType, content)
    private val audioTranscriptionId = UUID.randomUUID()

    private val audioTranscription = AudioTranscription(
        text = output,
        filename = fileName,
        user = user,
        id = audioTranscriptionId,
        isCompleted = true
    )

    @BeforeEach
    fun setUp() {
        audioTranscriptionReviewRepository = mock(AudioTranscriptionReviewRepository::class.java)
        transcriptionService = mock(TranscriptionService::class.java)
        userService = mock(UserService::class.java)
        audioTranscriptionReviewService = AudioTranscriptionReviewServiceImpl(
            audioTranscriptionReviewRepository,
            transcriptionService,
            userService
        )
    }

    @Test
    fun getAudioTranscriptionReviewFromAudioTranscriptionIdNullTest() {
        `when`(transcriptionService.getTranscriptionResult(audioTranscriptionId)).thenReturn(null)

        val result = audioTranscriptionReviewService.getAudioTranscriptionReviewFromAudioTranscriptionId(audioTranscriptionId)

        assert(result == null)
    }

    @Test
    fun getAudioTranscriptionReviewFromAudioTranscriptionIdTest() {

        `when`(transcriptionService.getTranscriptionResult(audioTranscriptionId)).thenReturn(audioTranscription)
        val expectedReview = AudioTranscriptionReview(audioTranscription, "Review Text", 4.5f)
        `when`(audioTranscriptionReviewRepository.findByAudioTranscription(audioTranscription)).thenReturn(expectedReview)

        val result = audioTranscriptionReviewService.getAudioTranscriptionReviewFromAudioTranscriptionId(audioTranscriptionId)

        assert(result == expectedReview)
    }

    @Test
    fun createAudioTranscriptionReviewNullTest() {
        `when`(transcriptionService.getTranscriptionResult(audioTranscriptionId)).thenReturn(null)

        val result = audioTranscriptionReviewService.createAudioTranscriptionReview(audioTranscriptionId, "Review Text", 4.5f)

        assert(result == null)
    }

    @Test
    fun createAudioTranscriptionReviewTest() {

        `when`(transcriptionService.getTranscriptionResult(audioTranscriptionId)).thenReturn(audioTranscription)
        val expectedReview = AudioTranscriptionReview(audioTranscription, "Review Text", 4.5f)
        `when`(audioTranscriptionReviewRepository.save(any(AudioTranscriptionReview::class.java))).thenReturn(expectedReview)

        val result = audioTranscriptionReviewService.createAudioTranscriptionReview(audioTranscriptionId, "Review Text", 4.5f)

        assert(result == expectedReview)
    }

    @Test
    fun updateAudioTranscriptionReviewNotFoundTest() {
        val id = UUID.randomUUID()
        `when`(audioTranscriptionReviewRepository.findById(id)).thenReturn(Optional.empty())

        val result = audioTranscriptionReviewService.updateAudioTranscriptionReview(id, "Updated Review Text", 3.0f)

        assert(result == null)
    }


    @Test
    fun updateAudioTranscriptionReviewTest() {
        val existingReview = AudioTranscriptionReview(audioTranscription, "hi", 1.0f, audioTranscriptionId)

        `when`(audioTranscriptionReviewRepository.findById(audioTranscriptionId)).thenReturn(Optional.of(existingReview))

        val updatedReviewText = "Updated Review Text"
        val updatedRating = 3.0f
        `when`(audioTranscriptionReviewRepository.save(existingReview)).thenReturn(existingReview.apply {
            reviewText = updatedReviewText
            reviewRating = updatedRating
        })

        val result = audioTranscriptionReviewService.updateAudioTranscriptionReview(audioTranscriptionId, updatedReviewText, updatedRating)

        verify(audioTranscriptionReviewRepository).save(existingReview)

        assertEquals(updatedReviewText, result?.reviewText)
        assertEquals(updatedRating, result?.reviewRating)
    }

    @Test
    fun deleteAudioTranscriptionReviewTrueTest() {
        doNothing(). `when`(audioTranscriptionReviewRepository).deleteById(audioTranscriptionId)

        val result = audioTranscriptionReviewService.deleteAudioTranscriptionReview(audioTranscriptionId)

        assert(result)
    }

    @Test
    fun deleteAudioTranscriptionReviewFalseTest() {
        doThrow(RuntimeException("Delete failed")). `when`(audioTranscriptionReviewRepository).deleteById(audioTranscriptionId)

        val result = audioTranscriptionReviewService.deleteAudioTranscriptionReview(audioTranscriptionId)

        assert(!result)
    }
}
