package com.example.vivochat.presentation.view.home.components

import CircleAvatar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UserStory() {
    Column(
        modifier = Modifier.width(70.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircleAvatar()
        Spacer(Modifier.height(4.dp))
        Text(
            "Omar Osama",
            maxLines = 1,
            fontSize = 10.sp,
            overflow = TextOverflow.Ellipsis
        )


    }
}