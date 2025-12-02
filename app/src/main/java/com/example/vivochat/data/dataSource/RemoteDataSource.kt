package com.example.vivochat.data.dataSource

import com.example.vivochat.data.dto.FirebaseMessage
import com.example.vivochat.data.dto.UserDto
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    fun sendMessage( senderId: String,
                     receiverId: String,message: FirebaseMessage)
    fun getConversation(userId:String,otherUserId:String): Flow<List<FirebaseMessage>>
    suspend fun getUsersList():List<UserDto>
    suspend fun uploadUserData(userId:String,fullName : String,email : String,phoneNumber: String): Result<Any>
    suspend fun getUserData(userId : String): Result<UserDto>
}