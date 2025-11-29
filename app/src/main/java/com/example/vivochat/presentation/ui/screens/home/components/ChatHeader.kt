package com.example.vivochat.presentation.ui.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vivochat.R

@Composable
fun ChatHeader() {
    Row(

        modifier = Modifier.fillMaxWidth().padding(horizontal = 7.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Chats", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        IconButton({}) {
            Icon(
                painter = painterResource(R.drawable.more), contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}