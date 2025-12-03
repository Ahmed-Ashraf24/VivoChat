package com.example.vivochat.data.dto

import com.example.vivochat.domain.entity.MediaType

data class StoryDto(
    val mediaUrl: String,
    val mediaType : MediaType
)