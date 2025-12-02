package com.example.vivochat.data.repository

import com.example.vivochat.data.dataSource.RemoteDataSource
import com.example.vivochat.data.dto.FirebaseMessage
import com.example.vivochat.data.mapper.MessageMapper
import com.example.vivochat.domain.entity.Message
import com.example.vivochat.domain.entity.Response
import com.example.vivochat.domain.entity.toFirebaseMessage
import com.example.vivochat.domain.repository.IMessageRep
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MessageRepo(private val remoteDataSource: RemoteDataSource): IMessageRep {
    override fun sendMessage(message: Message,recrverId:String) {
    remoteDataSource.sendMessage(senderId = message.senderId,recrverId,message.toFirebaseMessage())
    }

    override fun getMessages(userId:String,otherUserId:String): Flow<List<Message>> =flow{
        remoteDataSource.getConversation(userId,otherUserId).collect {
            emit(it.map(MessageMapper::toMessage))
        }
    }
}