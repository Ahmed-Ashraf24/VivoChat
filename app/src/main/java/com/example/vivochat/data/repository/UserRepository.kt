package com.example.vivochat.data.repository

import com.example.vivochat.data.dataSource.RemoteDataSource
import com.example.vivochat.data.dto.UserDto
import com.example.vivochat.data.mapper.UserMapper
import com.example.vivochat.data.mappers.toUser
import com.example.vivochat.domain.entity.User
import com.example.vivochat.domain.repository.IUserRepository

class UserRepository(
    private val remoteDataSource: RemoteDataSource
) : IUserRepository {

    override suspend fun uploadUserData(
        userId: String,
        fullName: String,
        email: String,
        phoneNumber: String
    ): Result<Any> {
        val response = remoteDataSource.uploadUserData(userId, fullName, email, phoneNumber)
        if (response.isSuccess) {
            return Result.success("Data uploaded successfully")
        }

        return Result.failure(Exception("Failed to upload user data"))
    }

    override suspend fun getUserData(userId: String): Result<User> {
        val response = remoteDataSource.getUserData(userId)

        if (response.isSuccess) {
            val userDto = response.getOrNull()!!
            return Result.success(userDto.toUser())
        }

        return Result.failure(Exception("failed to get user data"))

    }

    override suspend fun getAllUsers(): Result<List<User>> {
        return try {
            Result.success(remoteDataSource.getUsersList().map (UserMapper::toUser))
        } catch (e:Exception){
            Result.failure(Exception("somthing happened when trying to get the users "))
        }
    }

}