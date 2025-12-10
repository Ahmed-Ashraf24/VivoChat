package com.example.vivochat.presentation.ui.screens.splash.viewmodel

sealed class SplashState{
    object Idle : SplashState()
    object AutoLoginSuccess : SplashState()
    object AutoLoginFailed : SplashState()
}