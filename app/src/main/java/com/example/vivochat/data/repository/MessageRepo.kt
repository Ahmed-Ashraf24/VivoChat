package com.example.vivochat.data.repository

import com.example.vivochat.data.dto.FirebaseMessage
import com.example.vivochat.domain.entity.Message
import com.example.vivochat.domain.entity.Response
import com.example.vivochat.domain.repository.IMessageRep

class MessageRepo: IMessageRep {
    override fun sendMessage(message: Message) {

    }

    override fun getMessages(): Response {
        return try {
            Response.Success<List<FirebaseMessage>>(listOf())
        }catch (e: Exception){
            Response.Failed(e.message?:"Unknown  Exception")
        }
    }
}