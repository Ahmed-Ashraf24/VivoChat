package com.example.vivochat.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vivochat.R
import com.example.vivochat.domain.entity.Message
import com.example.vivochat.domain.entity.MessageType

@Preview(showSystemUi = true)
@Composable
fun ConversationMessagesComponent(modifier: Modifier = Modifier) {
    val messageList = listOf(
        Message("ahmed", "hi", MessageType.MyMessage, "2:00AM", "222"),
        Message("mohamed", "hello", MessageType.OthersMessage, "2:02AM", "222"),
        Message("ahmed", "how are you", MessageType.MyMessage, "2:03AM", "222"),
        Message("mohamed", "fine", MessageType.OthersMessage, "2:05AM", "222"),
    )
    Column(modifier.fillMaxWidth()) {
    messageList.forEach {message ->

        when(message.messageType){
            MessageType.MyMessage -> {
                MyMessageComponent(
                    modifier = Modifier.align(Alignment.End),
                    message = message.message
                )
            }
            MessageType.OthersMessage-> {
                Row(modifier=Modifier.align(Alignment.Start)) {
                    Surface(Modifier.padding(top = 35.dp), shape = RoundedCornerShape(50.dp)) {
                    Image(painter = painterResource(R.drawable.image), contentDescription = "profile", modifier = Modifier
                        .size(50.dp),
                        contentScale = ContentScale.FillBounds
                    )
                    }
                    OthersMessageComponent(modifier = Modifier.padding(horizontal = 10.dp),message = message.message)
                }

            }

        }

    }
    }
}