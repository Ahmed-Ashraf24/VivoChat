package com.example.vivochat.presentation.viewModel.StoryViewModel

sealed class StoryState{
    object Idle : StoryState()
    object StoryLoading : StoryState()
    object StorySuccess : StoryState()
    data class StoryFailed (val message:String): StoryState()
}