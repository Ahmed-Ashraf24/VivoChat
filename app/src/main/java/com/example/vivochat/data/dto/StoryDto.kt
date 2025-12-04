package com.example.vivochat.data.dto

import com.example.vivochat.domain.entity.MediaType
import com.google.firebase.Timestamp

data class StoryDto(
    val storyId: String? = "",
    val imageUrl: String ="",
    val date: Timestamp = Timestamp(0, 0),
    val watchedBy: List<String>? =null
)