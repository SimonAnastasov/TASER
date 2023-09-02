package amon.taser.config.filters

import amon.taser.model.User
import amon.taser.service.UserService

import amon.taser.config.JwtAuthConstants
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter


class JWTAuthorizationFilter(
    authenticationManager: AuthenticationManager?,
    val userService: UserService
) :
    BasicAuthenticationFilter(authenticationManager) {


    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val header: String? = request.getHeader(JwtAuthConstants.HEADER_STRING)
        if (header == null || !header.startsWith(JwtAuthConstants.TOKEN_PREFIX)) {
            chain.doFilter(request, response)
            return
        }
        val token = getToken(header)
        SecurityContextHolder.getContext().authentication = token
        chain.doFilter(request, response)
    }

    public fun getToken(header: String): UsernamePasswordAuthenticationToken? {
        // parse the token.
        try {
            val user: String = JWT.require(Algorithm.HMAC256(JwtAuthConstants.SECRET.toByteArray()))
                .build()
                .verify(header.replace(JwtAuthConstants.TOKEN_PREFIX, ""))
                .subject ?: return null
            val userDetails: String = ObjectMapper().readValue(user, String::class.java)
            return UsernamePasswordAuthenticationToken(userDetails, "", null)
        }
        catch (e: Exception) {
            return null
        }
    }

    public fun getUserFromAuthorizationHeader(authorizationHeader: String?) : User? {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return null
        }

        val token = authorizationHeader.substring(7)
        
        val authorizationClaims = this.getToken(token)
        if (authorizationClaims != null) {
            val username: String = authorizationClaims.getName()
            val user = userService.getUserByUsername(username)

            return user
        }

        return null
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val url = request.requestURI
        return url.contains("register").or(url.contains("login"))
    }
}

