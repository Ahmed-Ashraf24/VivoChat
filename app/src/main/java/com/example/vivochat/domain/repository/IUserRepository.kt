package com.example.vivochat.domain.repository

interface IUserRepository{
   suspend fun uploadUserData(userId:String,fullName : String,email : String,phoneNumber: String): Result<Any>

}