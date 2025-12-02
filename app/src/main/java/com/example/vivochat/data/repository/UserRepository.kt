package com.example.vivochat.data.repository

import android.util.Log
import com.example.vivochat.data.dataSource.RemoteDataSource
import com.example.vivochat.data.dto.UserDto
import com.example.vivochat.data.mappers.convertToUserList
import com.example.vivochat.data.mappers.toUser
import com.example.vivochat.domain.entity.Contact
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

    override suspend fun getAllUsersData(): List<User> {
        val response: List<UserDto> = remoteDataSource.getUsersList()
        return convertToUserList(response)
    }

    override suspend fun filterContacts(contactList: List<Contact>): Pair<List<User>, List<Contact>> {
        val availableContacts: MutableList<User> = mutableListOf()
        val unAvailableContacts: MutableList<Contact> = mutableListOf()
        val allUsers: List<User> = getAllUsersData()



        for (i in contactList) {
            if(i.phoneNum=="01287106301"){
                Log.d("i found him","ahmed")
            }
            //search if it is in the all users
            var userFound = false
            for (j in allUsers) {
                if (i.phoneNum == j.phoneNum) {
                    //user found
                    availableContacts.add(j)
                    userFound = true
                    break
                }
            }
            if(!userFound){
                unAvailableContacts.add(i)
            }
        }

        return Pair(availableContacts, unAvailableContacts)
    }


}