package com.example.vivochat.presentation.viewModel.home_view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vivochat.data.dataSource.local.LocalDataSource
import com.example.vivochat.domain.repository.IUserRepository
import com.example.vivochat.presentation.viewModel.signup_view_model.SignupViewModel

class HomeViewModelFac(
    private val userRepository: IUserRepository,
    private val localDataSource: LocalDataSource
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(userRepository,localDataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}