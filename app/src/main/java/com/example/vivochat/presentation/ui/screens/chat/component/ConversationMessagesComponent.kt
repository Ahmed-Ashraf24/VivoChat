package com.example.vivochat.presentation.ui.screens.chat.component

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.vivochat.R
import com.example.vivochat.domain.entity.Message
import com.example.vivochat.domain.entity.MessageType

@Composable
fun ConversationMessagesComponent(modifier: Modifier = Modifier,messageList:List<Message>,profileImage:String?) {

    Column(modifier.fillMaxWidth()) {
        messageList.forEach {message ->

        when(message.messageType){
            MessageType.MyMessage -> {
                MyMessageComponent(
                    modifier = Modifier.padding(top = 15.dp).align(Alignment.End),
                    message = message.message,
                    date = message.messageDate
                )
            }
            MessageType.OthersMessage-> {
                Row(modifier=Modifier.align(Alignment.Start)) {
                    Surface(Modifier.padding(top = 15.dp), shape = RoundedCornerShape(50.dp)) {
                        if (profileImage!=null){
                        AsyncImage(model = profileImage, contentDescription = "profile", modifier = Modifier
                            .size(35.dp),
                            contentScale = ContentScale.FillBounds
                        )}
                        else
                            Image(painter = painterResource(R.drawable.anonymoususer), contentDescription = "profile", modifier = Modifier
                                .size(35.dp),
                                contentScale = ContentScale.FillBounds
                            )
                    }
                    OthersMessageComponent(modifier = Modifier.padding(horizontal = 5.dp),message = message.message, date = message.message)
                }

            }

        }

    }
    }
}