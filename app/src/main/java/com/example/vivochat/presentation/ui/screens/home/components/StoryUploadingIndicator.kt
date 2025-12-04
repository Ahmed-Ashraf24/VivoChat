package com.example.vivochat.presentation.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vivochat.presentation.ui.theme.Primary

@Composable
fun StoryUploadingIndicator() {
    Box(

        modifier = Modifier.fillMaxWidth()
            .height(300.dp)
            .background(color = Primary),
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator(color = Primary)
    }
}