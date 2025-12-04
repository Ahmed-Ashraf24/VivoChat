package com.example.vivochat.data.dataSource.firebase_remote_datasource

import com.example.vivochat.data.dataSource.RemoteDataSource
import com.example.vivochat.data.dataSource.firebase_remote_datasource.firebase_utility.FirebaseInstance
import com.example.vivochat.data.dto.FirebaseMessage
import com.example.vivochat.data.dto.LastMessageData
import com.example.vivochat.data.dto.StoryDto
import com.example.vivochat.data.dto.UserDto
import com.google.firebase.Timestamp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class FirebaseRemoteDataSource @Inject constructor() : RemoteDataSource {

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
        val userNode = FirebaseInstance.firebaseDatabase.getReference("users")
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
        val convKey = FirebaseInstance.firebaseDatabase
            .getReference("conversations")
            .push().key!!

        val meta = mapOf(
            "conversationId" to convKey,
            "lastMessage" to "",
            "lastMessageTime" to System.currentTimeMillis()
        )

        val usersRef = FirebaseInstance.firebaseDatabase.getReference("users")


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
        val convRef = FirebaseInstance.firebaseDatabase
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

            val usersRef = FirebaseInstance.firebaseDatabase.getReference("users")
            usersRef.child(senderId).child(receiverId).updateChildren(metaUpdate)
            usersRef.child(receiverId).child(senderId).updateChildren(metaUpdate)
        }
    }

    override fun getConversation(userId: String,otherUserId:String): Flow<List<FirebaseMessage>> =
        callbackFlow {
            val metaRef = FirebaseInstance.firebaseDatabase
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

                    FirebaseInstance.firebaseDatabase.getReference("conversations")
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
                    FirebaseInstance.firebaseDatabase.getReference("conversations")
                        .child(conversationId)
                        .removeEventListener(listener)
                }
            }
        }
    override fun getLastMessage(
        userId: String,
        otherUserId: String
    ): Flow<LastMessageData?>
            = callbackFlow {

        val ref = FirebaseInstance.firebaseDatabase
            .getReference("users")
            .child(userId)
            .child(otherUserId)

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.exists()) {
                    trySend(null)
                    return
                }

                val lastMessage = snapshot.child("lastMessage").getValue(String::class.java)
                val lastTime = snapshot.child("lastMessageTime").getValue(Long::class.java)

                if (lastMessage != null && lastTime != null ) {
                    trySend(
                        LastMessageData(
                            message = lastMessage,
                            timestamp = lastTime
                        )
                    )
                } else {
                    trySend(null)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        ref.addValueEventListener(listener)

        awaitClose {
            ref.removeEventListener(listener)
        }
    }

    override fun getLoggedUserIdOrNull(): String? {
       return FirebaseInstance.firebaseAuth.currentUser?.uid
    }

    override suspend fun getUsersList(): List<UserDto> {
        val result = FirebaseInstance.fireStore.collection("users").get().await()

        return result.documents.map { doc ->
            UserDto(
                fullName = doc.getString("fullName") ?: "",
                userId = doc.getString("userId") ?: "",
                phoneNum = doc.getString("phoneNum") ?: "",
                email = doc.getString("email") ?: "",
                imageUrl = doc.getString("imageUrl")?:""
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
            val user = UserDto(userId, fullName, email, phoneNumber,null)
            FirebaseInstance.fireStore.collection("users")
                .document(userId)
                .set(user)
                .await()

            return Result.success("data sent successfully")
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }


    override suspend fun getUserData(userId: String): Result<UserDto> {

        val response = FirebaseInstance.fireStore.collection("users")
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

    override suspend fun uploadUserImage(userId: String, imageUrl: String): Result<Any> {
        try {
            FirebaseInstance.fireStore.collection("users")
                .document(userId)
                .update("imageUrl", imageUrl)
                .await()
            return Result.success(Exception("image uploaded successfully"))
        } catch (e: Exception) {
            return Result.failure(Exception("couldn't upload user pic"))
        }
    }

    override suspend fun uploadStory(userId: String, imageUrl: String): Result<Any> {
        try {
            val storyId = UUID.randomUUID().toString()
            val story = StoryDto(storyId, imageUrl, Timestamp.now(), null)
            FirebaseInstance.fireStore.collection("users")
                .document(userId)
                .collection("stories")
                .document(storyId)
                .set(story)
                .await()

            return Result.success("Story uploaded")
        } catch (e: Exception) {
            return Result.failure(Exception("Failed to upload story"))
        }
    }

    override suspend fun getUserStories(userId: String): Result<List<StoryDto>> {

        try{
            val snapShot = FirebaseInstance.fireStore.collection("users")
                .document(userId)
                .collection("stories")
                .get()
                .await()
            val stories: List<StoryDto> = snapShot.documents.mapNotNull { doc ->
                doc.toObject(StoryDto::class.java)
            }
            return Result.success(stories)
        }catch (e : Exception){
            return Result.failure(Exception("failed to fetch user stories"))
        }
    }

    override suspend fun loginUser(
        email: String,
        password: String
    ): Result<String> {

        try{
            val result=FirebaseInstance.firebaseAuth.signInWithEmailAndPassword(email,password).await()
            return Result.success(result.user!!.uid)
        }catch (e : Exception){
            e.printStackTrace()
            return Result.failure(Exception("failed to login"))
        }
    }

    override suspend fun signUpUser(
        email: String,
        password: String
    ): Result<String> {
        try{
            val result=FirebaseInstance.firebaseAuth.createUserWithEmailAndPassword(email,password).await()
            return Result.success(result.user!!.uid)
        }catch (e : Exception){
            return Result.failure(Exception("failed to login"))
        }
    }


}