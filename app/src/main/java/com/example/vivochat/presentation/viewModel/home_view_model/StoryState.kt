package com.example.vivochat.presentation.viewModel.home_view_model

sealed class StoryState{
    object Idle : StoryState()
    object StoryLoading : StoryState()
    object StorySuccess : StoryState()
    data class StoryFailed (val message:String): StoryState()
}