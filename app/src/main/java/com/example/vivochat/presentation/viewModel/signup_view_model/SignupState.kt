package com.example.vivochat.presentation.viewModel.signup_view_model

import com.example.vivochat.presentation.viewModel.login_view_model.LoginState


sealed class SignupState{
    object Idle : SignupState()
    object Loading : SignupState()
    data class Success(val userId: String) : SignupState()
    data class Error(val message: String) : SignupState()

}