package com.example.vivochat.data.dataSource

import com.example.vivochat.data.dto.FirebaseMessage

interface RemoteDataSource {
    fun addMessage(message: FirebaseMessage)
    fun getMessages():List<FirebaseMessage>
}