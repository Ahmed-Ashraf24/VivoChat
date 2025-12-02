package com.example.vivochat.data.mapper

import com.example.vivochat.data.dataSource.firebase_remote_datasource.firebase_utility.FirebaseIstance
import com.example.vivochat.data.dto.FirebaseMessage
import com.example.vivochat.domain.entity.Message
import com.example.vivochat.domain.entity.MessageType

object MessageMapper {
    fun toMessage(firebaseMessage: FirebaseMessage): Message {
        return Message(
            senderId = firebaseMessage.senderId,
            senderName = "",
            message = firebaseMessage.message,
            messageType = if (firebaseMessage.senderId == FirebaseIstance.firebaseAuth.currentUser!!.uid!!) MessageType.MyMessage else MessageType.OthersMessage,
            messageDate = firebaseMessage.timestamp.toString(),
            senderProfileUrl = "TODO()"
        )
    }
}