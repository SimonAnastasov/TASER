package amon.taser

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootApplication
class TaserApplication {
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder(10)
    }
}

fun main(args: Array<String>) {
    runApplication<TaserApplication>(*args)
}

