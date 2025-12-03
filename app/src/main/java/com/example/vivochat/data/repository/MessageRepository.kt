package com.example.vivochat.data.repository

import com.example.vivochat.data.dataSource.RemoteDataSource
import com.example.vivochat.data.mapper.MessageMapper
import com.example.vivochat.domain.entity.LastMessagePreview
import com.example.vivochat.domain.entity.Message
import com.example.vivochat.domain.entity.toFirebaseMessage
import com.example.vivochat.domain.repository.IMessageRep
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MessageRepository(private val remoteDataSource: RemoteDataSource): IMessageRep {
    override fun sendMessage(message: Message,recrverId:String) {
    remoteDataSource.sendMessage(senderId = message.senderId,recrverId,message.toFirebaseMessage())
    }

    override fun getMessages(userId:String,otherUserId:String): Flow<List<Message>> =flow{
        remoteDataSource.getConversation(userId,otherUserId).collect {
            emit(it.map(MessageMapper::toMessage))
        }
    }

    override fun getLastMessage(
        userId: String,
        otherUserId: String
    ): Flow<LastMessagePreview> =flow {
       remoteDataSource.getLastMessage(userId,otherUserId).collect { lastMessageData->
           lastMessageData?.let { emit(MessageMapper.toLastMessagePreview(it)) }

        }
    }
}