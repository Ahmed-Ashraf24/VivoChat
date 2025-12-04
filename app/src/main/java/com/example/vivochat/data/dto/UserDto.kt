package com.example.vivochat.data.dto

data class UserDto(
    val userId: String = "",
    val fullName: String = "",
    val email: String = "",
    val phoneNum: String = "",
    val imageUrl: String? = null
)