package com.example.vivochat.presentation.ui.screens.story.viewmodel

sealed class UploadingStoryState {
    object  Idle: UploadingStoryState()
    object UploadingStoryLoading : UploadingStoryState()
    object UploadingStorySuccess : UploadingStoryState()
    object UploadingStoryFailed : UploadingStoryState()
}