package com.example.vivochat.data.dataSource

import com.example.vivochat.data.dto.FirebaseMessage
import com.example.vivochat.data.dto.LastMessageData
import com.example.vivochat.data.dto.StoryDto
import com.example.vivochat.data.dto.UserDto
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    fun sendMessage( senderId: String,
                     receiverId: String,message: FirebaseMessage)
    fun getConversation(userId:String,otherUserId:String): Flow<List<FirebaseMessage>>
    suspend fun getUsersList():List<UserDto>
    suspend fun uploadUserData(userId:String,fullName : String,email : String,phoneNumber: String): Result<Any>
    suspend fun getUserData(userId : String): Result<UserDto>
    suspend fun uploadUserImage(userId:String,imageUrl:String): Result<Any>
    fun getLastMessage(
        userId: String,
        otherUserId: String
    ): Flow<LastMessageData?>
    fun getLoggedUserIdOrNull():String?
    suspend fun uploadStory(userId:String,imageUrl:String): Result <Any>
    suspend fun getUserStories(userId:String):Result<List<StoryDto>>
    suspend fun loginUser(email: String,password:String):Result<String>
    suspend fun signUpUser(email: String,password:String):Result<String>
    suspend fun logoutUser()

}