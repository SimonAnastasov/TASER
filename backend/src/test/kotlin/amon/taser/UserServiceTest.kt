package amon.taser

import amon.taser.model.User
import amon.taser.repository.UserRepository
import amon.taser.service.impl.UserServiceImpl
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.springframework.security.core.userdetails.UserDetails
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class UserServiceImplTest {



    private lateinit var userService: UserServiceImpl

    @Mock
    private lateinit var userRepository: UserRepository


    @BeforeEach
    fun setUp() {
        userService = UserServiceImpl(userRepository)
    }

    @Test
    fun createUserTest() {
        val username = "testUser"
        val password = "password"

        val savedUser = User(username, password, username)

        Mockito.`when`(userRepository.save(ArgumentMatchers.any(User::class.java))).thenReturn(savedUser)

        assertEquals( savedUser,userService.createUser(username,password))
    }

    @Test
    fun testGetUser() {
        val userId = UUID.randomUUID()
        val user = User( "testUser", "password","testUser")

        Mockito.`when`(userRepository.findById(userId)).thenReturn(Optional.of(user))

        assertEquals( user,userService.getUser(userId))
    }

    @Test
    fun getUserWhenUserNotFoundTest() {
        val uuid = UUID.randomUUID()

        Mockito.`when`(userRepository.findById(uuid)).thenReturn(Optional.empty())
        assertThrows<java.lang.Exception> {
            userService.getUser(uuid)
        }
    }

    @Test
    fun getUserByUsernameFoundTest() {

        val user = User( "testUser", "password","testUser")

        Mockito.`when`(userRepository.findByUsername("testUser")).thenReturn(user)

        val result = userService.getUserByUsername("testUser")

        assertNotNull(result)
        assertEquals(user, result)
    }

    @Test
    fun getUserByUsernameNotFoundTest() {

        Mockito.`when`(userRepository.findByUsername("testUser")).thenReturn(null)

        val result = userService.getUserByUsername("testUser")

        assertNull(result)
    }


    @Test
    fun loadUserByUsernameExistsTest() {
        val username = "testUser"
        val user = User(username, "password", username)
        Mockito.`when`(userRepository.findByUsername(username)).thenReturn(user)

        assertEquals(username, userService.loadUserByUsername(username).username)
    }

    @Test
    fun loadUserByUsernameDoesntExistsTest() {
        val username = "testUser"
        Mockito.`when`(userRepository.findByUsername(username)).thenReturn(null)

        assertThrows<java.lang.Exception> {
            userService.loadUserByUsername(username)
        }
    }

    @Test
    fun checkUsernameAlreadyExistsTrueTest() {
        val username = "testUser"
        Mockito.`when`(userRepository.findByUsername(username)).thenReturn(User(username, "password", username))

        val usernameExists = userService.checkUsernameAlreadyExists(username)

        assertTrue(usernameExists)
    }

    @Test
    fun checkUsernameAlreadyExistsFalseTest() {
        val username = "testUser"
        Mockito.`when`(userRepository.findByUsername(username)).thenReturn(null)

        val usernameExists = userService.checkUsernameAlreadyExists(username)

        assertFalse(usernameExists)
    }
}
