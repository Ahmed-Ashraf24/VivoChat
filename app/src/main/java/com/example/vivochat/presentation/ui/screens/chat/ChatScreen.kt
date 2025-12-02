package com.example.vivochat.presentation.ui.screens.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vivochat.presentation.ui.screens.chat.component.ChatBottomBar
import com.example.vivochat.presentation.ui.screens.chat.component.ChatTopBar
import com.example.vivochat.presentation.ui.screens.chat.component.ConversationMessagesComponent


@Composable
fun ChatScreen(navController: NavController) {
    var message by remember { mutableStateOf("") }
    Scaffold(modifier = Modifier.imePadding(),
        containerColor = Color.White,
        topBar = {
            ChatTopBar(
                Modifier
                    .padding(top = 30.dp)
                    .padding(horizontal = 10.dp),
                onBackClicked = {navController.navigate("navscreen")}
            )
        },
        bottomBar = {
            ChatBottomBar(
                modifier = Modifier.padding(
                    vertical = 20.dp,
                    horizontal = 10.dp
                ), message = message,
                onMessageChange = {message=it}
            )
        }) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            ConversationMessagesComponent(Modifier.fillMaxWidth().padding(horizontal = 10.dp))
        }
    }
}