package amon.taser.config.filters

import amon.taser.config.JwtAuthConstants
import amon.taser.model.User
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.boot.json.JsonParseException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.*

class JwtAuthenticationFilter(authenticationManager: AuthenticationManager, userDetailsService: UserDetailsService, passwordEncoder: PasswordEncoder) : UsernamePasswordAuthenticationFilter() {
    private val authenticationManager: AuthenticationManager
    private val userDetailsService: UserDetailsService
    private val passwordEncoder: PasswordEncoder

    init {
        setAuthenticationManager(authenticationManager)
        this.authenticationManager = authenticationManager
        this.userDetailsService = userDetailsService
        this.passwordEncoder = passwordEncoder
    }

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse?): Authentication {
        var creds: User? = null
        try {
            val text = String(request.inputStream.readAllBytes(), StandardCharsets.UTF_8)
            println(text)
            creds = ObjectMapper().readValue(text, User::class.java)
        } catch (e: JsonParseException) {
            e.printStackTrace()
        } catch (e: JsonMappingException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        if (creds == null) {
//            throw new UserNotFoundException("Invalid credentials");
            return super.attemptAuthentication(request, response)
        }
        val userDetails = userDetailsService.loadUserByUsername(creds.username)
        if (!passwordEncoder.matches(creds.password, userDetails.password)) {
            throw Exception("Invalid credentials")
        }
        return authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(userDetails.username, creds.password, userDetails.authorities)
        )
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(request: HttpServletRequest?, response: HttpServletResponse, chain: FilterChain?, authResult: Authentication) {
        generateJwt(response, authResult)
        super.successfulAuthentication(request, response, chain, authResult)
    }

    @Throws(JsonProcessingException::class)
    fun generateJwt(response: HttpServletResponse, authResult: Authentication): String {
        val userDetails: org.springframework.security.core.userdetails.User = authResult.principal as org.springframework.security.core.userdetails.User
        val token: String = JWT.create()
                .withSubject(ObjectMapper().writeValueAsString(userDetails.username))
                .withExpiresAt(Date(System.currentTimeMillis() + JwtAuthConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(JwtAuthConstants.SECRET.toByteArray()))
        response.addHeader(JwtAuthConstants.HEADER_STRING, JwtAuthConstants.TOKEN_PREFIX + token)
        return JwtAuthConstants.TOKEN_PREFIX + token
    }
}
