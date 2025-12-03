package com.example.vivochat.domain.entity

data class Story(
    val id: String,
    val mediaUrl: String,
    val mediaType:MediaType,
    val timestamp: Long,
    val duration: Long
)
