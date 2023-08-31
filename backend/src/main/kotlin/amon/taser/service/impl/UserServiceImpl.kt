package amon.taser.service.impl

import amon.taser.model.User
import amon.taser.repository.UserRepository
import amon.taser.service.UserService
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl(
        val userRepository: UserRepository
): UserService {
    override fun createUser(username: String, password: String): User? {
        return userRepository.save(User(username, password, username))
    }

    override fun getUser(username: UUID): User? {
        return userRepository.findById(username).get()
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        val user = username?.let { userRepository.findByUsername(it) }
        if (user == null){
            throw Exception("User not found")
        } else {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.username)
                    .password(user.password)
                    .roles("USER")
                    .build()
        }
    }

    override fun checkUsernameAlreadyExists(username: String): Boolean {
        return userRepository.findByUsername(username) != null
    }
}