package com.example.vivochat.presentation.viewModel.home_view_model

sealed class HomeState{
    object Idle : HomeState()
    object DataLoading : HomeState()
    object DataSuccess : HomeState()
    object DataFailed : HomeState()


}