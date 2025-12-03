package com.example.vivochat.presentation.ui.screens.chat

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vivochat.data.dataSource.firebase_remote_datasource.FirebaseRemoteDataSource
import com.example.vivochat.data.dataSource.firebase_remote_datasource.firebase_utility.FirebaseIstance
import com.example.vivochat.data.repository.MessageRepository
import com.example.vivochat.domain.entity.Message
import com.example.vivochat.domain.entity.MessageType
import com.example.vivochat.presentation.ui.screens.chat.component.ChatBottomBar
import com.example.vivochat.presentation.ui.screens.chat.component.ChatTopBar
import com.example.vivochat.presentation.ui.screens.chat.component.ConversationMessagesComponent
import com.example.vivochat.presentation.viewModel.MessageViewModel


@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun ChatScreen(navController: NavController,reciverName:String, reciverId:String) {
    var message by remember { mutableStateOf("") }
    val messageViewModel= MessageViewModel(MessageRepository(FirebaseRemoteDataSource()))
    messageViewModel.getMessages(FirebaseIstance.firebaseAuth.currentUser!!.uid,reciverId)
    val messageList =messageViewModel.messageData.collectAsState()
    Scaffold(modifier = Modifier.imePadding(),
        containerColor = Color.White,
        topBar = {
            ChatTopBar(
                Modifier
                    .padding(top = 30.dp)
                    .padding(horizontal = 10.dp), userName = reciverName,
                onBackClicked = {navController.navigate("navscreen")}
            )
        },
        bottomBar = {
            ChatBottomBar(
                modifier = Modifier.padding(
                    vertical = 20.dp,
                    horizontal = 10.dp
                ), message = message,
                onMessageChange = {message=it},
                onSendClicked = {messageViewModel.sendMessage(
                    message = Message(
                        senderId = FirebaseIstance.firebaseAuth.currentUser!!.uid!!,
                        senderName ="ahmed",
                        message = message,
                        messageType = MessageType.MyMessage,
                        messageDate = "TODO()",
                        senderProfileUrl = "TODO()"
                    ),
                    reciverId = reciverId
                )
                message=""
                }
            )
        }) { innerPadding ->

        LazyColumn (Modifier.padding(innerPadding)) {
            item {
                ConversationMessagesComponent(Modifier.fillMaxWidth().padding(horizontal = 10.dp),messageList.value)

            }
        }
    }
}