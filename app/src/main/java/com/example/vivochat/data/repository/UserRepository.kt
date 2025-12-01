package com.example.vivochat.data.repository

import android.util.Log
import com.example.vivochat.data.dto.UserDto
import com.example.vivochat.domain.entity.User
import com.example.vivochat.domain.repository.IUserRepository
import com.example.vivochat.presentation.ui.screens.login.Login
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepository(
    private val firestore: FirebaseFirestore
) : IUserRepository {
    override suspend fun uploadUserData(
        userId: String,
        fullName: String,
        email: String,
        phoneNumber: String
    ): Result<Any> {

        try {
            val user = UserDto(userId, fullName, email, phoneNumber)

            firestore.collection("users")
                .document(userId)
                .set(user)
                .await()

            return Result.success("data sent successfully")
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun getUserData(userId: String): Result<User> {

        val response = firestore.collection("users")
            .document(userId)
            .get()
            .await()

           if(response.exists()){
               val dto = response.data
               Log.d("Dataaaaaaaaaaaaaaaaaaaaaaaa",dto.toString())
               return Result.failure(Exception("User not foundd"))
           }else{
               return Result.failure(Exception("User not found"))
           }
    }

}