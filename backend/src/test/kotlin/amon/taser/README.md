# Backend testing

## Testing service class methods with JUnit and Mockito 
### UserServiceImpl
The UserServiceImpl class consists of the following methods: 

**createUser**
   

     override fun createUser(username: String, password: String): User? {  
        return userRepository.save(User(username, password, username))  
        }  

 **getUser**

    override fun getUser(username: UUID): User? {  
    return userRepository.findById(username).get()  
    }  

 **getUserByUsername**

    override fun getUserByUsername(username: String): User? {  
    return userRepository.findByUsername(username)  
    }  

**loadUserByUsername**

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

**checkUsernameAlreadyExists**

    override fun checkUsernameAlreadyExists(username: String): Boolean {  
    return userRepository.findByUsername(username) != null  
    }
    
### Tests:
For testing the methods, we have written the following unit tests:
**createUserTest**

    @Test  
    fun createUserTest() {  
    val username = "testUser"  
    val password = "password"  
      
    val savedUser = User(username, password, username)  
      
    Mockito.`when`(userRepository.save(ArgumentMatchers.any(User::class.java))).thenReturn(savedUser)  
      
    assertEquals(userService.createUser(username,password), savedUser)  
    }

This unit test ensures the createUser function in UserService behaves as expected.
1.  Mocking Repository: Mockito is used to mock the UserRepository's save method to simulate database interactions.
2.  Asserting Success: The test checks if the created user matches the expected savedUser object, validating the user creation process.

**testGetUser**

    @Test  
    fun testGetUser() {  
    val userId = UUID.randomUUID()  
    val user = User( "testUser", "password","testUser")  
      
    Mockito.`when`(userRepository.findById(userId)).thenReturn(Optional.of(user))  
      
    assertEquals( user,userService.getUser(userId))  
    }
This unit test validates the getUser function in UserService when a user is successfully found in the repository.
1.  Mocking Repository: Mockito is used to mock the UserRepository's findById method, returning an Optional containing a user object.
2.  Assertion of Equality: The test checks if the retrieved user matches the expected user object, ensuring that user retrieval functions correctly.

**testGetUserWhenUserNotFound**

    @Test  
    fun testGetUserWhenUserNotFound() {  
    val uuid = UUID.randomUUID()  
      
    Mockito.`when`(userRepository.findById(uuid)).thenReturn(Optional.empty())  
    assertThrows<java.lang.Exception> {  
    userService.getUser(uuid)  
    }  
    }
This unit test verifies the behavior of the getUser function in UserService when a user is not found in the repository.
1.  Mocking Repository: Mockito is used to mock the UserRepository's findById method, returning an empty Optional to simulate the absence of a user.
2.  Exception Assertion: The test asserts that an exception (java.lang.Exception) is thrown when attempting to retrieve a non-existent user, ensuring proper error handling.

**testGetUserByUsernameFound**

    @Test  
    fun testGetUserByUsernameFound() {  
      
    val user = User( "testUser", "password","testUser")  
      
    Mockito.`when`(userRepository.findByUsername("testUser")).thenReturn(user)  
      
    val result = userService.getUserByUsername("testUser")  
      
    assertNotNull(result)  
    assertEquals(user, result)  
    }
This unit test verifies the getUserByUsername function in UserService when a user with the specified username is found in the repository.
1.  Mocking Repository: Mockito is used to mock the UserRepository's findByUsername method, returning the expected user object.
2.  Assertions and Validation: The test checks that the result of getUserByUsername is not null, and it also ensures that the retrieved user matches the expected user object based on the provided username.

**testGetUserByUsernameNotFound**

    @Test  
    fun testGetUserByUsernameNotFound() {  
      
    Mockito.`when`(userRepository.findByUsername("testUser")).thenReturn(null)  
      
    val result = userService.getUserByUsername("testUser")  
      
    assertNull(result)  
    }
 This unit test examines the behavior of the getUserByUsername function in UserService when a user with the specified username is not found in the repository.
1.  Mocking Repository: Mockito is used to mock the UserRepository's findByUsername method, returning null to simulate the absence of a user.
2.  Null Assertion: The test confirms that the result of getUserByUsername is indeed null when attempting to retrieve a user with a non-existent username. 

**loadUserByUsernameExists**

    @Test  
    fun loadUserByUsernameExists() {  
    val username = "testUser"  
    val user = User(username, "password", username)  
    Mockito.`when`(userRepository.findByUsername(username)).thenReturn(user)  
      
    assertEquals(username, userService.loadUserByUsername(username).username)  
    }
This unit test assesses the loadUserByUsername function in UserService when a user with the specified username exists in the repository.

**loadUserByUsernameDoesntExists**

    @Test  
    fun loadUserByUsernameDoesntExists() {  
    val username = "testUser"  
    Mockito.`when`(userRepository.findByUsername(username)).thenReturn(null)  
      
    assertThrows<java.lang.Exception> {  
    userService.loadUserByUsername(username)  
    }  }
 This unit test examines the loadUserByUsername function in UserService when a user with the specified username is not found in the repository.
**checkUsernameAlreadyExistsTrue**

    @Test  
    fun checkUsernameAlreadyExistsTrue() {  
    val username = "testUser"  
    Mockito.`when`(userRepository.findByUsername(username)).thenReturn(User(username, "password", username))  
      
    val usernameExists = userService.checkUsernameAlreadyExists(username)  
      
    assertTrue(usernameExists)  
    }
This unit test evaluates the checkUsernameAlreadyExists function in UserService when a user with the specified username already exists in the repository.
1.  Mocking Repository: Mockito is employed to mock the UserRepository's findByUsername method, returning an existing user object with the provided username.
2.  Assertion of Truth: The test confirms that the function correctly detects that the username already exists in the system, and it validates this by using the assertTrue assertion.

**checkUsernameAlreadyExistsFalse**

    @Test  
    fun checkUsernameAlreadyExistsFalse() {  
    val username = "testUser"  
    Mockito.`when`(userRepository.findByUsername(username)).thenReturn(null)  
      
    val usernameExists = userService.checkUsernameAlreadyExists(username)  
      
    assertFalse(usernameExists)  
    }
 This unit test examines the checkUsernameAlreadyExists function in UserService when a user with the specified username does not exist in the repository.
1.  Mocking Repository: Mockito is used to mock the UserRepository's findByUsername method, returning null to simulate the absence of a user with the provided username.
2.  Assertion of Falsehood: The test verifies that the function correctly determines that the username does not exist in the system, and it validates this by using the assertFalse assertion.

### TranscriptionServiceImpl
The TranscriptionServiceImpl class consists of the following methods:

    override fun startTranscription(file: MultipartFile, user: User?): AudioTranscription? {  
    return audioTranscriptionApi.startTranscription(file, user)  
    }  
      
    override fun checkTranscriptionStatus(id: UUID): Boolean {  
    return transcriptionRepository.findById(id).get().isCompleted  
    }  
      
    override fun getTranscriptionResult(id: UUID): AudioTranscription? {  
    return transcriptionRepository.findById(id).get()  
    }  
      
    override fun getTranscriptionsHistoryForUser(user: User): List<AudioTranscription> {  
    return transcriptionRepository.findAllByUserOrderByTimestampUpdatedDesc(user)  
    }
    
### Tests:
For testing the methods, we have written the following unit tests:

**startTranscriptionTest**

    @Test  
    fun testStartTranscription() {  
      
    `when`(audioTranscriptionApi.startTranscription(file, user)).thenReturn(expectedTranscription)  
      
    val result = audioTranscriptionService.startTranscription(file, user)  
      
    assertNotNull(result, "Expected a transcription to be returned")  
    assertEquals(expectedTranscription, result, "Transcription does not match expected result")  
    }  
**checkTranscriptionStatusTest**

    @Test  
    fun testCheckTranscriptionStatus() {  
    val completed = true  
      
    `when`(transcriptionRepository.findById(transcriptionId)).thenReturn(Optional.of(expectedTranscription))  
      
    val result = audioTranscriptionService.checkTranscriptionStatus(transcriptionId)  
      
    assertEquals(completed, result, "Transcription status does not match expected result")  
    }  

  
**getTranscriptionResultFoundTest**

    @Test  
    fun testGetTranscriptionResultFound() {  
      
    `when`(transcriptionRepository.findById(transcriptionId)).thenReturn(Optional.of(expectedTranscription))  
      
    val result = audioTranscriptionService.getTranscriptionResult(transcriptionId)  
      
    assertEquals(expectedTranscription, result, "Transcription does not match expected result")  
    }  

  **getTranscriptionResultNotFoundTest**

    @Test  
    fun testGetTranscriptionResultNotFound() {  
    val transcriptionId = UUID.randomUUID()  
      
    `when`(transcriptionRepository.findById(transcriptionId)).thenReturn(Optional.empty())  
      
      
    assertThrows<Exception> {  
    audioTranscriptionService.getTranscriptionResult(transcriptionId)  
    }  
      
    }  

  **getTranscriptionsHistoryForUserTesr**

    @Test  
    fun testGetTranscriptionsHistoryForUser() {  
    val user = User("testUser", "password", "testUser")  
    val expectedTranscriptions = listOf(  
    expectedTranscription,expectedTranscription,expectedTranscription  
      
    )  
      
    `when`(transcriptionRepository.findAllByUserOrderByTimestampUpdatedDesc(user)).thenReturn(expectedTranscriptions)  
      
    val result = audioTranscriptionService.getTranscriptionsHistoryForUser(user)  
      
    assertEquals(expectedTranscriptions, result, "Transcriptions list does not match expected result")  
      
    }
### AudioTranscriptionReviewServiceImplTest
The AudioTranscriptionReviewServiceImplTest class consists of the following methods: 

    fun getAudioTranscriptionReviewFromAudioTranscriptionId(audioTranscriptionId: UUID): AudioTranscriptionReview?  
    fun createAudioTranscriptionReview(audioTranscriptionId: UUID, reviewText: String, reviewRating: Float): AudioTranscriptionReview?  
    fun updateAudioTranscriptionReview(id: UUID, reviewText: String, reviewRating: Float): AudioTranscriptionReview?  
    fun deleteAudioTranscriptionReview(id: UUID): Boolean
   
   ### Tests:
For testing the methods, we have written the following unit tests:

**getAudioTranscriptionReviewFromAudioTranscriptionIdNullTest**

    @Test  
    fun getAudioTranscriptionReviewFromAudioTranscriptionIdNullTest() {  
    `when`(transcriptionService.getTranscriptionResult(audioTranscriptionId)).thenReturn(null)  
      
    val result = audioTranscriptionReviewService.getAudioTranscriptionReviewFromAudioTranscriptionId(audioTranscriptionId)  
      
    assert(result == null)  
    }  

  **getAudioTranscriptionReviewFromAudioTranscriptionIdTest**

    @Test  
    fun getAudioTranscriptionReviewFromAudioTranscriptionIdTest() {  
      
    `when`(transcriptionService.getTranscriptionResult(audioTranscriptionId)).thenReturn(audioTranscription)  
    val expectedReview = AudioTranscriptionReview(audioTranscription, "Review Text", 4.5f)  
    `when`(audioTranscriptionReviewRepository.findByAudioTranscription(audioTranscription)).thenReturn(expectedReview)  
      
    val result = audioTranscriptionReviewService.getAudioTranscriptionReviewFromAudioTranscriptionId(audioTranscriptionId)  
      
    assert(result == expectedReview)  
    }  

  **createAudioTranscriptionReviewNullTest**

    @Test  
    fun createAudioTranscriptionReviewNullTest() {  
    `when`(transcriptionService.getTranscriptionResult(audioTranscriptionId)).thenReturn(null)  
      
    val result = audioTranscriptionReviewService.createAudioTranscriptionReview(audioTranscriptionId, "Review Text", 4.5f)  
      
    assert(result == null)  
    }  

  **createAudioTranscriptionReviewTest**

    @Test  
    fun createAudioTranscriptionReviewTest() {  
      
    `when`(transcriptionService.getTranscriptionResult(audioTranscriptionId)).thenReturn(audioTranscription)  
    val expectedReview = AudioTranscriptionReview(audioTranscription, "Review Text", 4.5f)  
    `when`(audioTranscriptionReviewRepository.save(any(AudioTranscriptionReview::class.java))).thenReturn(expectedReview)  
      
    val result = audioTranscriptionReviewService.createAudioTranscriptionReview(audioTranscriptionId, "Review Text", 4.5f)  
      
    assert(result == expectedReview)  
    }  

  **updateAudioTranscriptionReviewNotFoundTest**

    @Test  
    fun updateAudioTranscriptionReviewNotFoundTest() {  
    val id = UUID.randomUUID()  
    `when`(audioTranscriptionReviewRepository.findById(id)).thenReturn(Optional.empty())  
      
    val result = audioTranscriptionReviewService.updateAudioTranscriptionReview(id, "Updated Review Text", 3.0f)  
      
    assert(result == null)  
    }  

  
  **updateAudioTranscriptionReviewTest**

    @Test  
    fun updateAudioTranscriptionReviewTest() {  
    val existingReview = AudioTranscriptionReview(audioTranscription, "hi", 1.0f, audioTranscriptionId)  
      
    `when`(audioTranscriptionReviewRepository.findById(audioTranscriptionId)).thenReturn(Optional.of(existingReview))  
      
    val updatedReviewText = "Updated Review Text"  
    val updatedRating = 3.0f  
    `when`(audioTranscriptionReviewRepository.save(existingReview)).thenReturn(existingReview.apply {  
    reviewText = updatedReviewText  
    reviewRating = updatedRating  
    })  
      
    val result = audioTranscriptionReviewService.updateAudioTranscriptionReview(audioTranscriptionId, updatedReviewText, updatedRating)  
      
    verify(audioTranscriptionReviewRepository).save(existingReview)  
      
    assertEquals(updatedReviewText, result?.reviewText)  
    assertEquals(updatedRating, result?.reviewRating)  
    }  

  **deleteAudioTranscriptionReviewTrueTest**

    @Test  
    fun deleteAudioTranscriptionReviewTrueTest() {  
    doNothing(). `when`(audioTranscriptionReviewRepository).deleteById(audioTranscriptionId)  
      
    val result = audioTranscriptionReviewService.deleteAudioTranscriptionReview(audioTranscriptionId)  
      
    assert(result)  
    }  

  **deleteAudioTranscriptionReviewFalseTest**

    @Test  
    fun deleteAudioTranscriptionReviewFalseTest() {  
    doThrow(RuntimeException("Delete failed")). `when`(audioTranscriptionReviewRepository).deleteById(audioTranscriptionId)  
      
    val result = audioTranscriptionReviewService.deleteAudioTranscriptionReview(audioTranscriptionId)  
      
    assert(!result)  
    }

