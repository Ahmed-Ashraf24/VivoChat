package com.example.vivochat.presentation.ui.screens.login.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vivochat.presentation.ui.theme.Primary

@Composable
fun LoginButton(onClick: () -> Unit) {

    Button(
        onClick = onClick,
        modifier = Modifier
            .height(57.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Primary,
        )
    ) {
        Text("Login", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}
