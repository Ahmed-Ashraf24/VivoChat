package com.example.vivochat.presentation.ui.screens.signup.viewmodel


sealed class SignupState{
    object Idle : SignupState()
    object Loading : SignupState()
    data class Success(val userId: String) : SignupState()
    data class Error(val message: String) : SignupState()
}