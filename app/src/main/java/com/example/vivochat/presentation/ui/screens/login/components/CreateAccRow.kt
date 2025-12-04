package com.example.vivochat.presentation.ui.screens.login.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.vivochat.presentation.ui.theme.Primary

@Composable
fun CreateAccRow(modifier: Modifier,onClick:()->Unit) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Don't have an account?", color = MaterialTheme.colorScheme.onSurface)
        TextButton(onClick) {
            Text("Signup", color = Primary)
        }
    }
}