package com.example.vivochat.presentation.viewModel.splash_view_model

sealed class SplashState{
    object Idle : SplashState()
    object AutoLoginSuccess : SplashState()
    object AutoLoginFailed : SplashState()
}