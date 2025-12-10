package com.example.vivochat.presentation.ui.screens.home.viewmodel

sealed class UserState{
    object Idle : UserState()
    object UserDataLoading : UserState()
    object UserDataSuccess : UserState()
    object AllSuccess : UserState()
    object UserDataFailed : UserState()


}