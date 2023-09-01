package amon.taser.service

import amon.taser.model.User
import org.springframework.security.core.userdetails.UserDetailsService
import java.util.UUID

interface UserService : UserDetailsService{
    fun createUser(username: String, password: String): User?
    fun getUser(username: UUID): User?
    fun getUserByUsername(username: String): User?
    fun checkUsernameAlreadyExists(username: String): Boolean
}