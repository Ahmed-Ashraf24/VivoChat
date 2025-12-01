package com.example.vivochat.data.dataSource

import com.example.vivochat.data.dto.FirebaseMessage
import com.example.vivochat.data.dto.UserDto

interface RemoteDataSource {
    fun sendMessage(message: FirebaseMessage)
    fun getConversation(userId:String):List<FirebaseMessage>
    suspend fun getUsersList():List<UserDto>
}