package com.example.vivochat.data.dataSource

import android.util.Log
import com.example.vivochat.data.dto.FirebaseMessage
import com.example.vivochat.data.dto.UserDto
import com.example.vivochat.domain.entity.User
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await

class FirebaseRemoteDataSource() : RemoteDataSource {
    val firebaseDatabase = FirebaseDatabase.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    override fun sendMessage(message: FirebaseMessage) {
        TODO("Not yet implemented")
    }

    override fun getConversation(userId: String): List<FirebaseMessage> {


        return emptyList()
    }

    override suspend fun getUsersList(): List<UserDto> {
        val result = firestore.collection("users").get().await()

        return result.documents.map { doc ->
            UserDto(
                fullName = doc.getString("fullName") ?: "",
                userId = doc.getString("userId") ?: "",
                phoneNum = doc.getString("phoneNum") ?: "",
                email = doc.getString("email") ?: ""
            )
        }
    }
}