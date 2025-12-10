package com.example.vivochat.presentation.ui.screens.home.components

import CircleAvatar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vivochat.presentation.ui.screens.home.viewmodel.HomeViewModel

@Composable
fun ChatItem(lastMessagePreview: String="",timeOfMessage:String?,name:String,imageUrl:String?,onChatClicked:()->Unit,viewModel: HomeViewModel) {

    Row(

        modifier = Modifier.fillMaxWidth().padding(horizontal = 7.dp).clickable{
            onChatClicked()
        },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
        ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircleAvatar(imageUrl)
            Column(
                verticalArrangement = Arrangement.Center,
            ) {
                Text(name, fontSize = 17.sp)
                Spacer(Modifier.height(5.dp))
                Text(
                    text = lastMessagePreview,
                    maxLines = 1,
                    fontSize = 12.sp,
                    overflow = TextOverflow.Ellipsis,
                    color = Color(0xFF9E9E9E)
                )

            }
        }
        Text(timeOfMessage?:"")
    }
}