package com.example.vivochat.domain.entity

import com.example.vivochat.data.dto.FirebaseMessage

data class Message(
    val senderId: String,
    val senderName: String,
    val message: String,
    val messageType: MessageType,
    val messageDate: String,
    val senderProfileUrl: String
)
fun Message.toFirebaseMessage(): FirebaseMessage{
  return  FirebaseMessage(
        messageId = "",
        message = this.message,
        senderId = this.senderId,
        timestamp = 0L
    )
}