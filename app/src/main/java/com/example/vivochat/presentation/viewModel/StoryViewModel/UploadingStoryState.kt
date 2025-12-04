package com.example.vivochat.presentation.viewModel.StoryViewModel

sealed class UploadingStoryState {
    object  Idle: StoryState()
    object UploadingStoryLoading : StoryState()
    object UploadingStorySuccess : StoryState()
    object UploadingStoryFailed : StoryState()
}