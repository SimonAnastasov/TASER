import amon.taser.model.User
import amon.taser.model.AudioTranscription
import amon.taser.repository.TranscriptionRepository
import amon.taser.service.AudioTranscriptionApi
import amon.taser.service.impl.TranscriptionServiceImpl
import amon.taser.service.impl.output
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile
import java.util.*
import kotlin.test.assertFailsWith

@ExtendWith(MockitoExtension::class)
class TranscriptionServiceImplTest {

    private lateinit var audioTranscriptionService: TranscriptionServiceImpl
    @Mock
    private val audioTranscriptionApi: AudioTranscriptionApi = mock(AudioTranscriptionApi::class.java)
    @Mock
    private val transcriptionRepository: TranscriptionRepository = mock(TranscriptionRepository::class.java)

    private val user = User("testUser", "password", "testUser")
    private val fileName = "dummy-file.txt"
    private val contentType = "text/plain"
    private val content = "This is the file content.".toByteArray()

    private val file: MultipartFile = MockMultipartFile(fileName, fileName, contentType, content)
    private val transcriptionId = UUID.randomUUID()

    private val expectedTranscription = AudioTranscription(
        text = output,
        filename = fileName,
        user = user,
        id = transcriptionId,
        isCompleted = true
    )

    @BeforeEach
    fun setUp() {
        audioTranscriptionService = TranscriptionServiceImpl(audioTranscriptionApi, transcriptionRepository)
    }

    @Test
    fun startTranscriptionTest() {
        `when`(audioTranscriptionApi.startTranscription(file, user)).thenReturn(expectedTranscription)

        val result = audioTranscriptionService.startTranscription(file, user)

        assertNotNull(result, "Expected a transcription to be returned")
        assertEquals(expectedTranscription, result, "Transcription does not match expected result")
    }

    @Test
    fun checkTranscriptionStatusTest() {
        val completed = true

        `when`(transcriptionRepository.findById(transcriptionId)).thenReturn(Optional.of(expectedTranscription))

        val result = audioTranscriptionService.checkTranscriptionStatus(transcriptionId)

        assertEquals(completed, result, "Transcription status does not match expected result")
    }

    @Test
    fun getTranscriptionResultTest() {
        `when`(transcriptionRepository.findById(transcriptionId)).thenReturn(Optional.of(expectedTranscription))

        val result = audioTranscriptionService.getTranscriptionResult(transcriptionId)

        assertEquals(expectedTranscription, result, "Transcription does not match expected result")
    }

    @Test
    fun getTranscriptionResultNotFoundTest() {
        val nonExistentTranscriptionId = UUID.randomUUID()

        `when`(transcriptionRepository.findById(nonExistentTranscriptionId)).thenReturn(Optional.empty())

        assertFailsWith<Exception> {
            audioTranscriptionService.getTranscriptionResult(nonExistentTranscriptionId)
        }
    }

    @Test
    fun getTranscriptionsHistoryForUserTest() {
        val expectedTranscriptions = listOf(expectedTranscription, expectedTranscription, expectedTranscription)

        `when`(transcriptionRepository.findAllByUserOrderByTimestampUpdatedDesc(user)).thenReturn(expectedTranscriptions)

        val result = audioTranscriptionService.getTranscriptionsHistoryForUser(user)

        assertEquals(expectedTranscriptions, result, "Transcriptions list does not match expected result")
    }
}
