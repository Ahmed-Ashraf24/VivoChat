package com.example.vivochat.presentation.viewModel.signup_view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vivochat.data.dataSource.local.LocalDataSource
import com.example.vivochat.domain.repository.IUserRepository

class SignupViewModelFac(
    private val userRepository: IUserRepository,
    private val localDataSource: LocalDataSource
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignupViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SignupViewModel(userRepository,localDataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}