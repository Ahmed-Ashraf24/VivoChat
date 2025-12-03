package com.example.vivochat.data.dataSource.firebase_remote_datasource

import com.example.vivochat.data.dataSource.RemoteDataSource
import com.example.vivochat.data.dataSource.firebase_remote_datasource.firebase_utility.FirebaseIstance
import com.example.vivochat.data.dto.FirebaseMessage
import com.example.vivochat.data.dto.UserDto
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseRemoteDataSource() : RemoteDataSource {

    override fun sendMessage( senderId: String,receiverId: String,message: FirebaseMessage) {
        initConversation(senderId, receiverId) { conversationId ->
            uploadMessage(conversationId, senderId, receiverId, message)
        }

    }
    private fun initConversation(
        senderId: String,
        receiverId: String,
        onReady: (String) -> Unit
    ) {
        val userNode = FirebaseIstance.firebaseDatabase.getReference("users")
            .child(senderId)
            .child(receiverId)

        userNode.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    val conversationId = snapshot.child("conversationId").value.toString()
                    onReady(conversationId)
                } else {

                    createConversation(senderId, receiverId, onReady)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
    private fun createConversation(
        senderId: String,
        receiverId: String,
        onReady: (String) -> Unit
    ) {
        val convKey = FirebaseIstance.firebaseDatabase
            .getReference("conversations")
            .push().key!!

        val meta = mapOf(
            "conversationId" to convKey,
            "lastMessage" to "",
            "lastMessageTime" to System.currentTimeMillis()
        )

        val usersRef = FirebaseIstance.firebaseDatabase.getReference("users")


        usersRef.child(senderId).child(receiverId).setValue(meta)
        usersRef.child(receiverId).child(senderId).setValue(meta)
            .addOnSuccessListener { onReady(convKey) }
    }
    private fun uploadMessage(
        conversationId: String,
        senderId: String,
        receiverId: String,
        message: FirebaseMessage
    ) {
        val convRef = FirebaseIstance.firebaseDatabase
            .getReference("conversations")
            .child(conversationId)
            .push()

        val messageData = mapOf(
            "senderId" to senderId,
            "message" to message.message,
            "timestamp" to System.currentTimeMillis()
        )

        convRef.setValue(messageData).addOnSuccessListener {
            val metaUpdate = mapOf(
                "lastMessage" to message.message,
                "lastMessageTime" to System.currentTimeMillis()
            )

            val usersRef = FirebaseIstance.firebaseDatabase.getReference("users")
            usersRef.child(senderId).child(receiverId).updateChildren(metaUpdate)
            usersRef.child(receiverId).child(senderId).updateChildren(metaUpdate)
        }
    }

    override fun getConversation(userId: String,otherUserId:String): Flow<List<FirebaseMessage>> =
        callbackFlow {
            val metaRef = FirebaseIstance.firebaseDatabase
                .getReference("users")
                .child(userId)
                .child(otherUserId)
            var conversationListener: ValueEventListener? = null

            val metaListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {


                    val conversationId = snapshot.child("conversationId").getValue(String::class.java)
                    if (!snapshot.exists() || conversationId == null) {
                        trySend(emptyList())
                        return
                    }

                    FirebaseIstance.firebaseDatabase.getReference("conversations")
                        .child(conversationId)
                        .addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val messages = snapshot.children.mapNotNull { it.getValue(FirebaseMessage::class.java) }
                                trySend(messages)
                            }
                            override fun onCancelled(error: DatabaseError) {
                                close(error.toException())
                            }
                        })
                }
                override fun onCancelled(error: DatabaseError) {
                    cancel("Conversation metadata cancelled", error.toException())
                }
            }

            metaRef.addValueEventListener(metaListener)
            awaitClose {
                metaRef.removeEventListener(metaListener)
                conversationListener?.let { listener ->
                    val conversationId =
                        metaRef.child("conversationId").toString()
                    FirebaseIstance.firebaseDatabase.getReference("conversations")
                        .child(conversationId)
                        .removeEventListener(listener)
                }
            }
        }

    override suspend fun getUsersList(): List<UserDto> {
        val result = FirebaseIstance.firestore.collection("users").get().await()

        return result.documents.map { doc ->
            UserDto(
                fullName = doc.getString("fullName") ?: "",
                userId = doc.getString("userId") ?: "",
                phoneNum = doc.getString("phoneNum") ?: "",
                email = doc.getString("email") ?: ""
            )
        }
    }

    override suspend fun uploadUserData(
        userId: String,
        fullName: String,
        email: String,
        phoneNumber: String
    ): Result<Any> {
        try {
            val user = UserDto(userId, fullName, email, phoneNumber, "")
            FirebaseIstance.firestore.collection("users")
                .document(userId)
                .set(user)
                .await()

            return Result.success("data sent successfully")
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }


    override suspend fun getUserData(userId: String): Result<UserDto> {

        val response = FirebaseIstance.firestore.collection("users")
            .document(userId)
            .get()
            .await()

        if (response.exists()) {

            val userDto = response.toObject(UserDto::class.java)

            return Result.success(userDto!!)
        } else {
            return Result.failure(Exception("User not found"))
        }
    }

}