import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class TestConfig {

    @Bean
    fun mockPasswordEncoder(): PasswordEncoder {
        return NoOpPasswordEncoder.getInstance() // Use NoOpPasswordEncoder for testing purposes
    }

    // You can define other beans or mock beans for your tests here

}
