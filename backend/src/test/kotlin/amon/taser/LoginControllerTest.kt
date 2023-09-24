package amon.taser

import amon.taser.config.filters.JwtAuthenticationFilter
import amon.taser.model.User

import amon.taser.repository.UserRepository
import amon.taser.service.impl.UserServiceImpl
import amon.taser.web.LoginRestController
import amon.taser.web.RegisterController
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class LoginRestControllerTest {
    private lateinit var mockMvc: MockMvc

    private lateinit var loginRestController: LoginRestController


    private lateinit var jwtAuthenticationFilter: JwtAuthenticationFilter
    @Mock
    private lateinit var authenticationManager: AuthenticationManager
    @Mock
    private lateinit var userDetailsService: UserDetailsService
    @Mock
    private lateinit var passwordEncoder: PasswordEncoder

    val createdUser = User("username", "encodedPassword", "username")

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        jwtAuthenticationFilter = JwtAuthenticationFilter(authenticationManager,userDetailsService,passwordEncoder)
        loginRestController = LoginRestController(jwtAuthenticationFilter)
        mockMvc = MockMvcBuilders.standaloneSetup(loginRestController).build()
    }





    @Test
    fun testInvalidLogin() {
        val request = MockHttpServletRequest()
        val response = MockHttpServletResponse()

       var user=  org.springframework.security.core.userdetails.User.builder()
            .username(createdUser.username)
            .password(createdUser.password)
            .roles("USER")
            .build()

        Mockito.`when`(passwordEncoder.matches("","")).thenReturn(false)
        mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("You entered invalid credentials."))
    }
}
