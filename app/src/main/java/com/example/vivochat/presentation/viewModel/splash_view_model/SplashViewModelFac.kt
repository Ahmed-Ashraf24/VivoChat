package com.example.vivochat.presentation.viewModel.splash_view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vivochat.data.dataSource.local.LocalDataSource
import com.example.vivochat.presentation.viewModel.login_view_model.LoginViewModel


class SplashViewModelFac(
    private val localDataSource: LocalDataSource
)  : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SplashViewModel(localDataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}