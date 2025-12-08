package com.example.vivochat.domain.entity

import com.google.firebase.Timestamp
import kotlinx.serialization.Serializable

data class Story(
    val storyId: String,
    val imageUrl:String,
    val watchedBy:List<String>?,
    val date : Timestamp
)
