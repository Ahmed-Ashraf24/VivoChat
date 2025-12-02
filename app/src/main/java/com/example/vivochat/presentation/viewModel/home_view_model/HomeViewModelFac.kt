package com.example.vivochat.presentation.viewModel.home_view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vivochat.domain.repository.IUserRepository
import com.google.firebase.auth.FirebaseAuth

class HomeViewModelFac(
    private val userRepository: IUserRepository,
    private val firebaseAuth: FirebaseAuth
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(userRepository,firebaseAuth) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}