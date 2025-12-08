package com.example.vivochat.domain.entity

import kotlinx.serialization.Serializable

data class User (
    val userId: String,
    var fullName:String,
    val email : String,
    val phoneNum : String,
    val imageUrl : String?,
    var stories : List<Story>?
)