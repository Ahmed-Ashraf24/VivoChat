package com.example.vivochat.presentation.viewModel.StoryViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vivochat.domain.entity.Story
import com.example.vivochat.domain.entity.User
import com.example.vivochat.domain.repository.IMediaRepository
import com.example.vivochat.domain.repository.IUserRepository
import com.example.vivochat.presentation.viewModel.StoryViewModel.StoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
@HiltViewModel
class StoryViewModel @Inject constructor(
    private val mediaRepo: IMediaRepository,
    private val userRepo: IUserRepository,
) : ViewModel(){
    private var _storyState = MutableStateFlow<StoryState>(StoryState.Idle)
    val storyState: StateFlow<StoryState>
        get() = _storyState

    private var _uploadingStoryState = MutableStateFlow<StoryState>(UploadingStoryState.Idle)
    val uploadingStoryState: StateFlow<StoryState>
        get() = _uploadingStoryState

    val stories : MutableList<Story> = mutableListOf()

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

    fun getAvaUsersStories(availableContacts: List<User>,user: User){
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
}