package com.example.vivochat.presentation.viewModel.media_viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vivochat.data.dataSource.firebase_remote_datasource.firebase_utility.FirebaseInstance
import com.example.vivochat.domain.repository.IMediaRepository
import com.example.vivochat.domain.repository.IUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
@HiltViewModel
class MediaViewModel @Inject constructor(
    private val mediaRepo: IMediaRepository,
    private val userRepository: IUserRepository
) : ViewModel() {

     var imageUrl:String?=null

    private val _mediaState = MutableStateFlow<MediaState>(MediaState.Idle)
    val mediaState: StateFlow<MediaState>
        get() = _mediaState


    fun uploadImage(file: File) {
        viewModelScope.launch {
            _mediaState.value = MediaState.Loading

            mediaRepo.uploadImage(file)
                .onSuccess { url ->
                     imageUrl = url

                    val res = userRepository.uploadUserImage(
                        FirebaseInstance.firebaseAuth.currentUser!!.uid,
                        url
                    )

                    if(res.isSuccess){

                        _mediaState.value = MediaState.Success
                    }else{

                        _mediaState.value = MediaState.Failure(res.isFailure.toString())
                    }
                }
                .onFailure { exception ->

                    _mediaState.value = MediaState.Failure(exception.toString())
                }
        }
    }

    fun uploadVideo(file: File) {
        viewModelScope.launch {
//            _isLoading.value = true
//            _error.value = null
//
//            mediaRepo.uploadVideo(file)
//                .onSuccess { result ->
//                    _videoUrl.value = result.streamUrl
//                    _isLoading.value = false
//                }
//                .onFailure { exception ->
//                    _error.value = exception.message
//                    _isLoading.value = false
//                }
        }
    }


}

