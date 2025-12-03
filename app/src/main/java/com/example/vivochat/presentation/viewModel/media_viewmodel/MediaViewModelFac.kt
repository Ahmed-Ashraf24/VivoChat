package com.example.vivochat.presentation.viewModel.media_viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vivochat.domain.repository.IMediaRepository
import com.example.vivochat.domain.repository.IUserRepository
import com.example.vivochat.presentation.viewModel.splash_view_model.SplashViewModel

class MediaViewModelFac(
    private val mediaRepo: IMediaRepository,
    private val userRepository: IUserRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MediaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MediaViewModel(mediaRepo,userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}