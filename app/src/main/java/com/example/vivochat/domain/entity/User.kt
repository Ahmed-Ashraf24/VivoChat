package com.example.vivochat.domain.entity

data class User(
    val userId: String,
    val fullName:String,
    val email : String,
    val phoneNum : String,
    val imageUrl : String?
)