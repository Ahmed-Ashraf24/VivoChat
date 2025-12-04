package com.example.vivochat.presentation.viewModel.home_view_model

sealed class UserState{
    object Idle : UserState()
    object UserDataLoading : UserState()
    object UserDataSuccess : UserState()
    object AllSuccess : UserState()
    object UserDataFailed : UserState()


}