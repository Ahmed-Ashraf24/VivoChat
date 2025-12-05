package com.example.vivochat.presentation.viewModel.user_view_model

sealed class UserState{
    object Idle : UserState()
    object UserDataLoading : UserState()
    object UserDataSuccess : UserState()
    object UserDataFailed : UserState()
    object AllSuccess : UserState()


}