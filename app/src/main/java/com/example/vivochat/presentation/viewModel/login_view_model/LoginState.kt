package com.example.vivochat.presentation.viewModel.login_view_model

sealed class LoginState{
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val userId: String) : LoginState()
    data class Error(val message: String) : LoginState()
}