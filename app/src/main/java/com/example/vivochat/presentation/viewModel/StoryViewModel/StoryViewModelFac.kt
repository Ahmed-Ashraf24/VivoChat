package com.example.vivochat.presentation.viewModel.StoryViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vivochat.domain.repository.IMediaRepository
import com.example.vivochat.domain.repository.IUserRepository
import com.example.vivochat.presentation.viewModel.signup_view_model.SignupViewModel

class StoryViewModelFac (
    private val mediaRepo: IMediaRepository,
    private val userRepo: IUserRepository,
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StoryViewModel(mediaRepo,userRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}