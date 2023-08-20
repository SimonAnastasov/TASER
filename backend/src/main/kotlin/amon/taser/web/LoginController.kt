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
    ): String {
        val auth: Authentication = filter.attemptAuthentication(request, response)
        return filter.generateJwt(response, auth)
    }
}
