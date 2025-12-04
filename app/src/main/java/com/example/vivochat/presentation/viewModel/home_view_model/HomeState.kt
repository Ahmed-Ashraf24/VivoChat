package com.example.vivochat.presentation.viewModel.home_view_model

sealed class HomeState{
    object Idle : HomeState()
    object UserDataLoading : HomeState()
    object UserDataSuccess : HomeState()
    object AllSuccess : HomeState()
    object UserDataFailed : HomeState()


}