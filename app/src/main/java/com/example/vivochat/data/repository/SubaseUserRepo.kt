package com.example.vivochat.data.repository

import com.example.vivochat.domain.entity.User
import com.example.vivochat.domain.repository.IUserRepository

class SubaseUserRepo: IUserRepository {
    override suspend fun uploadUserData(
        userId: String,
        fullName: String,
        email: String,
        phoneNumber: String
    ): Result<Any> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserData(userId: String): Result<User> {
        TODO("Not yet implemented")
    }
}