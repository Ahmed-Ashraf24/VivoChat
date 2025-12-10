package com.example.vivochat.data.repository

import android.util.Log
import com.example.vivochat.data.dataSource.RemoteDataSource
import com.example.vivochat.data.dto.StoryDto
import com.example.vivochat.data.mapper.StoryMapper
import com.example.vivochat.data.mapper.UserMapper
import com.example.vivochat.data.mappers.toUser
import com.example.vivochat.domain.entity.Contact
import com.example.vivochat.domain.entity.Story
import com.example.vivochat.domain.entity.User
import com.example.vivochat.domain.repository.IUserRepository
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : IUserRepository {
    private var cachedUsers: List<User>? = null
    private val cachedUserData = mutableMapOf<String, User>()
    private val cachedStories = mutableMapOf<String, List<Story>>()
    private var cachedFilterResult: Pair<List<User>, List<Contact>>? = null
    private var cachedContacts: List<Contact>? = null
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
        cachedUserData[userId]?.let {
            Log.d("cached user from repo",cachedUserData[userId].toString())
            return Result.success(it) }
        val response = remoteDataSource.getUserData(userId)

        if (response.isSuccess) {
            val userDto = response.getOrNull()!!
            cachedUserData[userId]=userDto.toUser()
            return Result.success(userDto.toUser())

        }

        return Result.failure(Exception("failed to get user data"))

    }


    override suspend fun getAllUsers(): Result<List<User>> {
        cachedUsers?.let { return Result.success(it) }

        return try {
            val users = remoteDataSource.getUsersList().map(UserMapper::toUser)
            cachedUsers = users
            Result.success(users)
        } catch (e: Exception) {
            Result.failure(Exception("Something happened when trying to get users"))
        }
    }

    override suspend fun filterContacts(contactList: List<Contact>): Pair<List<User>, List<Contact>> {

        if (cachedContacts == contactList && cachedFilterResult != null) {
            return cachedFilterResult!!
        }
        val availableContacts: MutableList<User> = mutableListOf()
        val unAvailableContacts: MutableList<Contact> = mutableListOf()
        val allUsers: MutableList<User> = mutableListOf()
        getAllUsers().onSuccess { users ->
            allUsers.addAll(users)
        }



        for (i in contactList) {


            var userFound = false
            for (j in allUsers) {
                if (i.phoneNum == j.phoneNum||i.phoneNum =="+2${j.phoneNum}") {

                    availableContacts.add(j)
                    userFound = true
                    break
                }
            }
            if(!userFound){
                unAvailableContacts.add(i)
            }
        }
        cachedContacts = contactList
        cachedFilterResult = Pair(availableContacts, unAvailableContacts)
        return cachedFilterResult!!
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
        cachedStories[userId]?.let { return Result.success(it) }
         try{
             val response = remoteDataSource.getUserStories(userId)
             val storiesDto  : List<StoryDto> = response.getOrNull()?:emptyList()

             val stories = StoryMapper.mapDtoListToDomainList(storiesDto)
             cachedStories[userId] = stories

             return Result.success(stories)
         }catch (e : Exception){
             return Result.failure(Exception("fail"))
         }
    }


    fun clearCache() {
        cachedUsers = null
        cachedUserData.clear()
        cachedStories.clear()
        cachedFilterResult=null
    }



}