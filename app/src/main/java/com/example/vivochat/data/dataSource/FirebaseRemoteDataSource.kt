package com.example.vivochat.data.dataSource

import com.example.vivochat.data.dto.FirebaseMessage
import com.example.vivochat.data.dto.UserDto
import com.example.vivochat.domain.entity.User
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class FirebaseRemoteDataSource(
    private val firestore: FirebaseFirestore
) : RemoteDataSource {

    override fun addMessage(message: FirebaseMessage) {
        TODO("Not yet implemented")
    }

    override fun getMessages(): List<FirebaseMessage> {
        TODO("Not yet implemented")
    }


    override suspend fun uploadUserData(
        userId: String,
        fullName: String,
        email: String,
        phoneNumber: String
    ): Result<Any> {

        try {
            val user = UserDto(userId, fullName, email, phoneNumber, null)
            firestore.collection("users")
                .document(userId)
                .set(user)
                .await()

            return Result.success("data sent successfully")
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }


    override suspend fun getUserData(userId: String): Result<UserDto> {


        val response = firestore.collection("users")
            .document(userId)
            .get()
            .await()

        if (response.exists()) {

            val userDto = response.toObject(UserDto::class.java)

            return Result.success(userDto!!)
        } else {
            return Result.failure(Exception("User not found"))
        }

    }
}