package com.example.vivochat.domain.entity

data class Message(
    val senderName: String,
    val message: String,
    val messageType: MessageType,
    val messageDate: String,
    val senderProfileUrl: String
)
