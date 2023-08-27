package amon.taser.web

import amon.taser.model.RegistrationDto
import amon.taser.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Controller
class RegisterController (
    val userService: UserService,
    val passwordEncoder: PasswordEncoder
) {
    @PostMapping("/api/register")
    fun register(
        @RequestBody registrationDto: RegistrationDto
    ):ResponseEntity<Any> {
        val username = registrationDto.username
        val password = passwordEncoder.encode(registrationDto.password)
        val createdUser = userService.createUser(username, password)
        return if (createdUser != null) {
            ResponseEntity.ok(createdUser)
        } else {
            ResponseEntity.badRequest().build()
        }
    }
}