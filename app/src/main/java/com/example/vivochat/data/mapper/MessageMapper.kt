package com.example.vivochat.data.mapper

import com.example.vivochat.data.dataSource.firebase_remote_datasource.firebase_utility.FirebaseInstance
import com.example.vivochat.data.dto.FirebaseMessage
import com.example.vivochat.data.dto.LastMessageData
import com.example.vivochat.domain.entity.LastMessagePreview
import com.example.vivochat.domain.entity.Message
import com.example.vivochat.domain.entity.MessageType
import com.example.vivochat.presentation.utility.TimeFormateUtility

object MessageMapper {
    fun toMessage(firebaseMessage: FirebaseMessage): Message {
        return Message(
            senderId = firebaseMessage.senderId,
            senderName = "",
            message = firebaseMessage.message,
            messageType = if (firebaseMessage.senderId == FirebaseInstance.firebaseAuth.currentUser!!.uid!!) MessageType.MyMessage else MessageType.OthersMessage,
            messageDate = TimeFormateUtility.formateTampToDayAndHour(firebaseMessage.timestamp)
        )
    }
    fun toLastMessagePreview(lastMessageData: LastMessageData): LastMessagePreview {
        return LastMessagePreview(
            message = lastMessageData.message,
            date = TimeFormateUtility.formateTampToHour(lastMessageData.timestamp)
        )
    }

}