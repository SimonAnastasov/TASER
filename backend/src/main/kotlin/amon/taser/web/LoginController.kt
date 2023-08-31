package amon.taser.web

import amon.taser.config.filters.JwtAuthenticationFilter
import com.fasterxml.jackson.core.JsonProcessingException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.ResponseEntity


@RestController
//@CrossOrigin(origins = ["http://localhost:3000", "http://localhost:3001"])
@RequestMapping("/api/login")
class LoginRestController(filter: JwtAuthenticationFilter) {
    private val filter: JwtAuthenticationFilter

    init {
        this.filter = filter
    }

    @PostMapping
    @Throws(JsonProcessingException::class)
    fun doLogin(
        request: HttpServletRequest,
        response: HttpServletResponse
    ):ResponseEntity<Any> {
        try {
            val auth: Authentication = filter.attemptAuthentication(request, response)
            val token = filter.generateJwt(response, auth)
            val body = mapOf(
                "bearerToken" to token
            )
            return ResponseEntity.ok(body);
        }
        catch (e: Exception) {
            val errorMessage = mapOf(
                "error" to true,
                "message" to "You entered invalid credentials."
            )
            return ResponseEntity.ok(errorMessage)
        }
    }
}
