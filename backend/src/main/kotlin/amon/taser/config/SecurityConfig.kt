package amon.taser.config

import amon.taser.config.filters.JWTAuthorizationFilter
import amon.taser.config.filters.JwtAuthenticationFilter
import amon.taser.service.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@Configuration
class JWTWebSecurityConfig(
    val passwordEncoder: PasswordEncoder,
    val userService: UserService
) {


    @Bean
    @Throws(java.lang.Exception::class)
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager? {
        return authenticationConfiguration.authenticationManager
    }

    @Throws(Exception::class)
    @Bean
    fun configure(http: HttpSecurity, authManager: AuthenticationManager): SecurityFilterChain {
        http.cors().and().csrf().disable()
            .authorizeRequests()
            .requestMatchers("/login","/api/**").permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .addFilter(authenticationFilter(authManager))
            .addFilter(authorizationFilter(authManager))
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        return http.build()
    }

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer? {
        return WebSecurityCustomizer { web: WebSecurity -> web.ignoring().requestMatchers("/api/register") }
    }


    @Bean
    @Throws(java.lang.Exception::class)
    fun authenticationFilter(authManager: AuthenticationManager): JwtAuthenticationFilter {
        return JwtAuthenticationFilter(authManager, userService, passwordEncoder)
    }
    @Bean
    @Throws(java.lang.Exception::class)
    fun authorizationFilter(authManager: AuthenticationManager): JWTAuthorizationFilter {
        return JWTAuthorizationFilter(authManager)
    }

}
