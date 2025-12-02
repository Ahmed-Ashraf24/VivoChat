package com.example.vivochat.presentation.viewModel.home_view_model

import com.example.vivochat.presentation.viewModel.signup_view_model.SignupState

sealed class HomeState{
    object Idle : HomeState()
    object UserDataLoading : HomeState()
    object UserDataSuccess : HomeState()
    object UserDataFailed : HomeState()
}