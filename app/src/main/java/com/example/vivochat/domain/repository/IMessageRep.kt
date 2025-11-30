package com.example.vivochat.domain.repository

import com.example.vivochat.domain.entity.Message
import com.example.vivochat.domain.entity.Response

interface IMessageRep {
    fun sendMessage(message: Message)
    fun getMessages(): Response
}