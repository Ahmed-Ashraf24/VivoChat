package com.example.vivochat.domain.repository

import com.example.vivochat.domain.entity.User

interface IUserRepository{
   suspend fun uploadUserData(userId:String,fullName : String,email : String,phoneNumber: String): Result<Any>
    suspend fun getUserData(userId : String): Result<User>
    suspend fun getAllUsers(): Result<List<User>>
}