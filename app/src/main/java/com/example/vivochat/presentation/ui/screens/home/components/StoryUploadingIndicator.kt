package com.example.vivochat.presentation.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.vivochat.presentation.ui.theme.Primary

@Composable
fun StoryUploadingIndicator() {
    Dialog({
    }){
        Box(

            modifier = Modifier.width(100.dp)
                .height(90.dp)
                .background(color = Color(0xFFE5E5EA), shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator(color = Primary)
        }
    }
}