package com.example.vivochat.presentation.ui.screens.profile_image.viewmodel

sealed class MediaState{
    object Idle : MediaState()
    object Loading : MediaState()
    object Success: MediaState()
    data class Failure(val message: String) : MediaState()
}