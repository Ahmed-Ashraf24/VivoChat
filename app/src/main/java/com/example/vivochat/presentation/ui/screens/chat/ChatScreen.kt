package com.example.vivochat.presentation.ui.screens.chat

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.vivochat.domain.entity.User
import com.example.vivochat.presentation.ui.screens.chat.component.ChatBottomBar
import com.example.vivochat.presentation.ui.screens.chat.component.ChatTopBar
import com.example.vivochat.presentation.ui.screens.chat.component.ConversationMessagesComponent
import com.example.vivochat.presentation.ui.screens.chat.viewmodel.ChatViewModel
import com.example.vivochat.presentation.utility.UserNavType
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
data class ChatRoute(val fullName:String,val reciverId: String,val imageUrl:String)

fun NavGraphBuilder.chatScreen(navController: NavController){
    composable<ChatRoute>(typeMap = mapOf(typeOf<User>() to UserNavType)) { backStackEntry ->
        val route = backStackEntry.toRoute<ChatRoute>()
        ChatScreen(
            navController = navController,
            reciverName = route.fullName,
            reciverId = route.reciverId,
            reciverImageUrl = route.imageUrl,
        )
    }
}


@Composable
fun ChatScreen(
    messageViewModel: ChatViewModel = hiltViewModel(),
    navController: NavController,
    reciverName: String,
    reciverId: String,
    reciverImageUrl: String
) {
    var message by remember { mutableStateOf("") }
    messageViewModel.getMessages(reciverId)
    val messageList = messageViewModel.messageData.collectAsState()
    messageViewModel.getState(reciverId)
    val isOnline=messageViewModel.isOnline.collectAsState()
    Scaffold(
        modifier = Modifier.imePadding(),
        topBar = {
            ChatTopBar(
                Modifier
                    .padding(top = 30.dp)
                    .padding(horizontal = 10.dp),
                userImageUrl = reciverImageUrl,
                userName = reciverName,
                isOnline=isOnline.value,
                onBackClicked = { navController.popBackStack() }
            )
        },
        bottomBar = {
            ChatBottomBar(
                modifier = Modifier.padding(
                    vertical = 20.dp,
                    horizontal = 10.dp
                ), message = message,
                onMessageChange = { message = it },
                onSendClicked = {
                    messageViewModel.sendMessage(
                        message = message,
                        receiver = reciverId
                    )
                    message = ""
                }
            )
        }) { innerPadding ->

        LazyColumn(Modifier.padding(innerPadding)) {
            item {
                ConversationMessagesComponent(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    messageList.value,
                    profileImage = reciverImageUrl
                )

            }
        }
    }
}