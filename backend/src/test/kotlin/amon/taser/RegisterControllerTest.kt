package amon.taser

import amon.taser.model.RegistrationDto
import amon.taser.model.User
import amon.taser.repository.UserRepository
import amon.taser.service.impl.UserServiceImpl
import amon.taser.web.RegisterController
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders


@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [TaserApplication::class]) // Specify your main application class
@ExtendWith(MockitoExtension::class)
class RegisterControllerTest {
    private lateinit var mockMvc: MockMvc
    private lateinit var registerController: RegisterController

    @Mock
    private lateinit var passwordEncoder: PasswordEncoder

    private lateinit var userService: UserServiceImpl

    @Mock
    private lateinit var userRepository: UserRepository

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        userService = UserServiceImpl(userRepository)
        registerController = RegisterController(userService,passwordEncoder)
        mockMvc = MockMvcBuilders.standaloneSetup(registerController).build()
        mockMvc = MockMvcBuilders.standaloneSetup(registerController).build()
    }

    val createdUser = User("username", "encodedPassword", "username")


    @Test
    @Throws(Exception::class)
    fun testRegistrationSuccess() {

        Mockito.`when`(passwordEncoder.encode("password")).thenReturn("encodedPassword")
        Mockito.`when`(userRepository.save(ArgumentMatchers.any(User::class.java))).thenReturn(createdUser)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/register")
                .contentType("application/json")
                .content("{\"username\":\"username\",\"password\":\"password\"}")
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(""))
    }

    @Test
    fun testRegistrationUsernameExists() {

        val (username, password) = RegistrationDto("username", "password")
        Mockito.`when`(userRepository.findByUsername("username")).thenReturn(createdUser)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/register")
                .contentType("application/json")
                .content("{\"username\":\"$username\",\"password\":\"$password\"}")
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.error").value(true))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.message")
                    .value("Username already exists. Please choose another username.")
            )



    }
}