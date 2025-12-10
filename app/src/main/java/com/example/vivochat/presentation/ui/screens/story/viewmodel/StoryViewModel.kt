package com.example.vivochat.presentation.ui.screens.story.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vivochat.domain.entity.Story
import com.example.vivochat.domain.entity.User
import com.example.vivochat.domain.repository.IAuthRepo
import com.example.vivochat.domain.repository.IMediaRepository
import com.example.vivochat.domain.repository.IUserRepository
import com.example.vivochat.presentation.utility.ContactUtility
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
@HiltViewModel
class StoryViewModel @Inject constructor(
    private val mediaRepo: IMediaRepository,
    private val authRepo: IAuthRepo,
    private val userRepo: IUserRepository,
    private val contactUtility: ContactUtility

) : ViewModel(){
    private var _storyState = MutableStateFlow<StoryState>(StoryState.Idle)
    val storyState: StateFlow<StoryState>
        get() = _storyState

    private var _uploadingStoryState = MutableStateFlow<UploadingStoryState>(UploadingStoryState.Idle)
    val uploadingStoryState: StateFlow<UploadingStoryState>
        get() = _uploadingStoryState

    private val _stories = MutableStateFlow<List<Story>>(emptyList())
    val stories : MutableList<Story> = mutableListOf()

    private val _userData = MutableStateFlow<User>(User(
        userId = "",
        fullName = "",
        email = "",
        phoneNum = "",
        imageUrl = "",
        stories = emptyList()
    ))
    val userData= _userData.asStateFlow()
     private val _availableContacts=MutableStateFlow<List<User>>(emptyList())
     val availableContacts=_availableContacts.asStateFlow()

    init {
        getCurrentUserData()
        getAvaUsersStories(availableContacts.value,userData.value)
    }
    private fun getCurrentUserData(){
        val phoneContacts = contactUtility.getContact()

        viewModelScope.launch {
            val res = userRepo.getUserData(
                authRepo.getLoggedUserIdOrNull()!!
            )
            if(res.isSuccess){
                _userData.value = res.getOrNull()!!
                Log.d("user data from story view model",_userData.value.toString())
                val filteredData=userRepo.filterContacts(phoneContacts)
                val dbUsers = filteredData.first


                _availableContacts.value = dbUsers

                loadStoriesForContacts(dbUsers)
                 }
        }
    }
    fun uploadStory(file: File,user:User){
        _uploadingStoryState.value = UploadingStoryState.UploadingStoryLoading
        var imageUrl : String
        viewModelScope.launch {
            mediaRepo.uploadImage(file)
                .onSuccess { url ->
                    imageUrl = url

                    val res = userRepo.uploadStory(user.userId,imageUrl)

                    if(res.isSuccess){
                        val resList = userRepo.getUserStories(user.userId).getOrNull() ?: emptyList()
                        user.stories = resList
                        _uploadingStoryState.value = UploadingStoryState.UploadingStorySuccess
                    }else{

                        _uploadingStoryState.value = UploadingStoryState.UploadingStoryFailed
                    }
                }
                .onFailure { exception ->
                    _uploadingStoryState.value = UploadingStoryState.UploadingStoryFailed
                }
        }
    }

    private fun loadStoriesForContacts(users: List<User>) {
        viewModelScope.launch {
            val updatedList = users.map { user ->
                val stories = userRepo.getUserStories(user.userId).getOrNull().orEmpty()
                user.copy(stories = stories)
            }
            _availableContacts.value = updatedList
        }
    }
    fun getAvaUsersStories(availableContacts: List<User>,user: User){
        _storyState.value = StoryState.StoryLoading
        Log.d("HSOIUhdsai","ddd")
        try{
            viewModelScope.launch {

                user.apply {
                   stories = userRepo.getUserStories(userId).getOrNull()
                }
                Log.d("user stories from story view model",user.stories.toString())

                for(i in availableContacts){
                    val res =  userRepo.getUserStories(i.userId).getOrNull()?:emptyList()
                    i.stories = res
                    stories.addAll(res)
                }
                Log.d("The resspone ",stories.toString())
                _storyState.value = StoryState.StorySuccess

            }
        }catch (e : Exception){
            _storyState.value = StoryState.StoryFailed(e.toString())
        }

    }
    fun resetUploadedStoryState(){
        _uploadingStoryState.value = UploadingStoryState.Idle
    }
}