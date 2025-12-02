package com.example.vivochat.data.dataSource

import com.example.vivochat.data.dto.FirebaseMessage
import com.example.vivochat.domain.entity.User
import com.example.vivochat.data.dto.UserDto

interface RemoteDataSource {
    fun addMessage(message: FirebaseMessage)
    fun getMessages():List<FirebaseMessage>

    suspend fun uploadUserData(userId:String,fullName : String,email : String,phoneNumber: String): Result<Any>
    suspend fun getUserData(userId : String): Result<UserDto>
}