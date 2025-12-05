package com.example.vivochat.presentation.viewModel.StoryViewModel

sealed class UploadingStoryState {
    object  Idle: UploadingStoryState()
    object UploadingStoryLoading : UploadingStoryState()
    object UploadingStorySuccess : UploadingStoryState()
    object UploadingStoryFailed : UploadingStoryState()
}