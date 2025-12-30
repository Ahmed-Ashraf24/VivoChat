package com.example.vivochat.domain.repository

import com.example.vivochat.data.dataSource.UserPresence
import com.example.vivochat.domain.entity.LastMessagePreview
import com.example.vivochat.domain.entity.Message
import kotlinx.coroutines.flow.Flow

interface IMessageRep {
    fun sendMessage(message: Message,recrverId:String)
    fun getMessages(userId:String,otherUserId:String): Flow<List<Message>>
    fun getLastMessage(userId:String,otherUserId:String): Flow<LastMessagePreview>
    fun observeUser(userId: String):Flow<UserPresence>
    fun setStateI(userId: String)
}