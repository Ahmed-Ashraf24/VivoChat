package com.example.vivochat.domain.entity

data class UserStories(
    val userId: String,
    val username: String,
    val profilePicUrl: String,
    val stories: List<Story>,
    var currentIndex: Int = 0
)
