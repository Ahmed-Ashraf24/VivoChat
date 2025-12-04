package com.example.vivochat.presentation.viewModel.user_view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vivochat.domain.entity.Contact
import com.example.vivochat.domain.entity.User
import com.example.vivochat.domain.repository.IAuthRepo
import com.example.vivochat.domain.repository.IMediaRepository
import com.example.vivochat.domain.repository.IUserRepository
import com.example.vivochat.presentation.utility.ContactUtility
import com.example.vivochat.presentation.viewModel.StoryViewModel.StoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
@HiltViewModel
class UserViewModel @Inject constructor(
    private val mediaRepo: IMediaRepository,
    private val userRepo: IUserRepository,
    private val authRpp: IAuthRepo,
    private val contactUtility: ContactUtility
) : ViewModel() {

    lateinit var user: User
    lateinit var availableContacts: List<User>
    lateinit var unAvailableContacts: List<Contact>

    private var _userDataState = MutableStateFlow<UserState>(UserState.Idle)
    val userData: StateFlow<UserState>
        get() = _userDataState


    fun getUserData() {
        try {
            _userDataState.value = UserState.UserDataLoading
            val phoneContacts = contactUtility.getContact()
            viewModelScope.launch {
                val userId = authRpp.getLoggedUserIdOrNull()!!
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


    init {
        getUserData()
    }








    fun resetState(){
        _userDataState.value = UserState.Idle
    }



    private var _storyState = MutableStateFlow<StoryState>(StoryState.Idle)
    val storyState: StateFlow<StoryState>
        get() = _storyState


    fun uploadStory(file: File){
        var imageUrl : String
        viewModelScope.launch {


            mediaRepo.uploadImage(file)
                .onSuccess { url ->
                    imageUrl = url

                    val res = userRepo.uploadStory(user.userId,imageUrl)

                    if(res.isSuccess){

                        _storyState.value = StoryState.StorySuccess
                    }else{

                        _storyState.value = StoryState.StoryFailed(res.isFailure.toString())
                    }
                }
                .onFailure { exception ->

                    _storyState.value = StoryState.StoryFailed(exception.toString())
                }
        }
    }
}