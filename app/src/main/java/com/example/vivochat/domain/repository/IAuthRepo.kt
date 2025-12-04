package com.example.vivochat.domain.repository

interface IAuthRepo {
    suspend fun loginUser(email:String,password:String):Result<String>
    suspend fun signUpUser(email:String,password:String): Result<String>
    suspend fun signOutUser()
    fun getLoggedUserIdOrNull():String?

}