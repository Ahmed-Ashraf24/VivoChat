package com.example.vivochat.presentation.viewModel.signup_view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vivochat.domain.repository.IUserRepository
import com.google.firebase.auth.FirebaseAuth

class SignupViewModelFac(
    private val userRepository: IUserRepository,
    private val firebaseAuth: FirebaseAuth
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignupViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SignupViewModel(userRepository,firebaseAuth) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}