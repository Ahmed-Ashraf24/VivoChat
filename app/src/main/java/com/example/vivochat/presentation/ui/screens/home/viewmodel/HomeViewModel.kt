package com.example.vivochat.presentation.ui.screens.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vivochat.domain.entity.Contact
import com.example.vivochat.domain.entity.LastMessagePreview
import com.example.vivochat.domain.entity.Story
import com.example.vivochat.domain.entity.User
import com.example.vivochat.domain.repository.IAuthRepo
import com.example.vivochat.domain.repository.IMediaRepository
import com.example.vivochat.domain.repository.IMessageRep
import com.example.vivochat.domain.repository.IUserRepository
import com.example.vivochat.presentation.utility.ContactUtility
import com.example.vivochat.presentation.ui.screens.story.viewmodel.StoryState
import com.example.vivochat.presentation.ui.screens.story.viewmodel.UploadingStoryState
import com.example.vivochat.presentation.ui.screens.home.viewmodel.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepo: IAuthRepo,
    private val messageRepo: IMessageRep,
    private val userRepo: IUserRepository,
    private val mediaRepo: IMediaRepository,
    private val contactUtility: ContactUtility

) : ViewModel() {
    lateinit var availableContacts: List<User>
    lateinit var unAvailableContacts: List<Contact>
    private var _storyState = MutableStateFlow<StoryState>(StoryState.Idle)
    val storyState: StateFlow<StoryState>
        get() = _storyState

    private var _uploadingStoryState =
        MutableStateFlow<UploadingStoryState>(UploadingStoryState.Idle)
    val uploadingStoryState: StateFlow<UploadingStoryState>
        get() = _uploadingStoryState

    val stories : MutableList<Story> = mutableListOf()

    private var _userDataState = MutableStateFlow<UserState>(UserState.Idle)
    val userDataState: StateFlow<UserState>
        get() = _userDataState
    var user = User(
        userId = "",
        fullName = "",
        email = "",
        phoneNum = "",
        imageUrl = "",
        stories = emptyList()
    )
    private val _lastMessages = MutableStateFlow<Map<String, LastMessagePreview?>>(emptyMap())
    val lastMessages: StateFlow<Map<String, LastMessagePreview?>> = _lastMessages

    init {
        getUserData()
    }

    fun getUserData() {
        try {
            _userDataState.value = UserState.UserDataLoading
            val phoneContacts = contactUtility.getContact()
            viewModelScope.launch {
                val userId = authRepo.getLoggedUserIdOrNull()!!
                val res = userRepo.getUserData(userId)

                if (res.isSuccess) {
                    user = res.getOrNull()!!

                    val pair = userRepo.filterContacts(phoneContacts)
                    availableContacts = pair.first
                    unAvailableContacts = pair.second
                    _userDataState.value = UserState.UserDataSuccess


                } else {
                    _userDataState.value = UserState.UserDataFailed
                }

            }
        } catch (e: Exception) {
            Log.d("catch the error", e.toString())
        }
    }


    fun uploadStory(file: File, user: User){
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

    fun getAvaUsersStories(availableContacts: List<User>, user: User){
        _storyState.value = StoryState.StoryLoading
        Log.d("HSOIUhdsai","ddd")
        try{
            viewModelScope.launch {

                user.stories = userRepo.getUserStories(user.userId).getOrNull()


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



    fun changeStateToAllSuccess() {
        _userDataState.value = UserState.AllSuccess
    }
    fun getLastMessage(receiverId:String){
        viewModelScope.launch {
            messageRepo.getLastMessage(authRepo.getLoggedUserIdOrNull()!!,receiverId).collect { lastMessage->
                Log.d("get last message",lastMessage.message)
                val updated = _lastMessages.value.toMutableMap()
                updated[receiverId] = lastMessage

                _lastMessages.value = updated
            }
        }
    }
}