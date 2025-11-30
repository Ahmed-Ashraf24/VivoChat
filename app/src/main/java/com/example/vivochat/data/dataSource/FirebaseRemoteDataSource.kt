package com.example.vivochat.data.dataSource

import com.example.vivochat.data.dto.FirebaseMessage
import com.google.firebase.database.FirebaseDatabase

class FirebaseRemoteDataSource(): RemoteDataSource {
    val firebaseDatabase= FirebaseDatabase.getInstance()
    override fun addMessage(message: FirebaseMessage) {
        TODO("Not yet implemented")
    }

    override fun getMessages(): List<FirebaseMessage> {
        TODO("Not yet implemented")
    }
}