package com.example.vivochat.data.repository

import android.util.Log
import com.example.vivochat.data.dataSource.RemoteDataSource
import com.example.vivochat.data.dto.StoryDto
import com.example.vivochat.data.dto.UserDto
import com.example.vivochat.data.mapper.StoryMapper
import com.example.vivochat.data.mapper.UserMapper
import com.example.vivochat.data.mappers.convertToUserList
import com.example.vivochat.data.mappers.toUser
import com.example.vivochat.domain.entity.Contact
import com.example.vivochat.domain.entity.Story
import com.example.vivochat.domain.entity.User
import com.example.vivochat.domain.repository.IUserRepository
import javax.inject.Inject

class UserRepository @Inject constructor(
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

    override suspend fun filterContacts(contactList: List<Contact>): Pair<List<User>, List<Contact>> {
        val availableContacts: MutableList<User> = mutableListOf()
        val unAvailableContacts: MutableList<Contact> = mutableListOf()
        val allUsers: MutableList<User> = mutableListOf()
        getAllUsers().onSuccess { users ->
            allUsers.addAll(users)
        }



        for (i in contactList) {


            var userFound = false
            for (j in allUsers) {
                if (i.phoneNum == j.phoneNum) {

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

    override suspend fun uploadUserImage(
        userId: String,
        imageUrl: String
    ): Result<Any> {
         val response = remoteDataSource.uploadUserImage(userId,imageUrl)
        if(response.isSuccess){
            return Result.success("Image uploaded successfully")
        }else{
            return Result.failure(Exception("failed to upload user pic"))
        }
    }

    override suspend fun uploadStory(userId: String, imageUrl: String): Result<Any> {
         val res = remoteDataSource.uploadStory(userId,imageUrl)
        if(res.isSuccess){
            return Result.success("story uploaded successfully")
        }else{
            return Result.failure(Exception("failed to upload story"))
        }
    }
    override suspend fun getUserStories(userId: String): Result<List<Story>> {
         try{
             val response = remoteDataSource.getUserStories(userId)
             val storiesDto  : List<StoryDto> = response.getOrNull()?:emptyList()
             //map
             val stories = StoryMapper.mapDtoListToDomainList(storiesDto)

             return Result.success(stories)
         }catch (e : Exception){
             return Result.failure(Exception("fail"))
         }
    }






}