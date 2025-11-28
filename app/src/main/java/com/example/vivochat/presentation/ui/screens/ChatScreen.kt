package com.example.vivochat.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vivochat.presentation.ui.component.ChatBottomBar
import com.example.vivochat.presentation.ui.component.ChatTopBar
import com.example.vivochat.presentation.ui.component.ConversationMessagesComponent

@Preview(showSystemUi = true)
@Composable
fun ChatScreen(modifier: Modifier = Modifier) {
    Scaffold(
        containerColor = Color.White,
        topBar = {
            ChatTopBar(
                Modifier
                    .padding(top = 30.dp)
                    .padding(horizontal = 10.dp)
            )
        },
        bottomBar = {
            ChatBottomBar(
                modifier = Modifier.padding(
                    vertical = 20.dp,
                    horizontal = 10.dp
                ), message = ""
            )
        }) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            ConversationMessagesComponent(Modifier.fillMaxWidth().padding(horizontal = 10.dp))
        }
    }
}