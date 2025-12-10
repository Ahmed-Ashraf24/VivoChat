package com.example.vivochat.data.repository

import com.example.vivochat.data.dataSource.RemoteDataSource
import com.example.vivochat.domain.repository.IAuthRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository@Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : IAuthRepo {
    override suspend fun loginUser(email: String, password: String): Result<String>{
        return remoteDataSource.loginUser(email,password)
    }

    override suspend fun signUpUser(
        email: String,
        password: String
    ): Result<String> {
        return remoteDataSource.signUpUser(email,password)
    }

    override suspend fun signOutUser() {
        remoteDataSource.logoutUser()
    }
    override fun getLoggedUserIdOrNull(): String? {
        return remoteDataSource.getLoggedUserIdOrNull()
    }
}