package com.example.vivochat.data.repository

import com.example.vivochat.data.dto.UserDto
import com.example.vivochat.domain.repository.IUserRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepository(
    private val firestore: FirebaseFirestore
) : IUserRepository {
    override suspend fun uploadUserData(
        userId:String,
        fullName: String,
        email: String,
        phoneNumber: String
    ): Result<Any> {

       try{
           val user = UserDto(userId,fullName,email,phoneNumber)

           firestore.collection("users")
               .document()
               .set(user)
               .await()

           return Result.success("data sent successfully")
       }catch (e : Exception){
           return Result.failure(e)
       }
    }

}