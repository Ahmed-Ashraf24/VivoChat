package com.example.vivochat.domain.repository

import com.example.vivochat.domain.entity.Contact
import com.example.vivochat.domain.entity.User

interface IUserRepository{
   suspend fun uploadUserData(userId:String,fullName : String,email : String,phoneNumber: String): Result<Any>
    suspend fun getUserData(userId : String): Result<User>
    suspend fun getAllUsers(): Result<List<User>>
    suspend fun filterContacts(contactList:List<Contact>): Pair<List<User>,List<Contact>>
    suspend fun uploadUserImage(userId: String,imageUrl:String): Result<Any>
    suspend fun uploadStory(userId:String,imageUrl:String): Result<Any>
}