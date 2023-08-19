package amon.taser.service

import amon.taser.model.User
import java.util.UUID

interface UserService {
    fun createUser(username: String, password: String): User?
    fun getUser(username: UUID): User?
}