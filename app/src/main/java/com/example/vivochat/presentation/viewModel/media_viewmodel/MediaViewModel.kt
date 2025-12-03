package com.example.vivochat.presentation.viewModel.media_viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vivochat.domain.repository.IMediaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File

class MediaViewModel(private val mediaRepo: IMediaRepository): ViewModel() {

    private val _imageUrl = MutableStateFlow<String?>(null)
    val imageUrl: StateFlow<String?> = _imageUrl.asStateFlow()

    private val _videoUrl = MutableStateFlow<String?>(null)
    val videoUrl: StateFlow<String?> = _videoUrl.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun uploadImage(file: File) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            mediaRepo.uploadImage(file)
                .onSuccess { url ->
                    Log.d("image url",url)
                    _imageUrl.value = url
                    _isLoading.value = false
                }
                .onFailure { exception ->
                    _error.value = exception.message
                    _isLoading.value = false
                }
        }
    }

    fun uploadVideo(file: File) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            mediaRepo.uploadVideo(file)
                .onSuccess { result ->
                    _videoUrl.value = result.streamUrl
                    _isLoading.value = false
                }
                .onFailure { exception ->
                    _error.value = exception.message
                    _isLoading.value = false
                }
        }
    }

    fun clearError() {
        _error.value = null
    }
}

