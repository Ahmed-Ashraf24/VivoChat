package com.example.vivochat.presentation.ui.screens.login.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun LoginHeader(modifier: Modifier = Modifier) {
    Text(
        text = "Vivo Chat",
        fontSize = 36.sp,
        fontWeight = FontWeight.Bold,
        modifier =modifier,
        color = MaterialTheme.colorScheme.onSurface
    )
}