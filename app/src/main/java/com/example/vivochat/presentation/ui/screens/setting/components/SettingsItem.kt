package com.example.vivochat.presentation.ui.screens.setting.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vivochat.presentation.ui.theme.Primary
import com.example.vivochat.presentation.ui.theme.kumbuhFont

@Composable
fun SettingsItem(title: String, subtitle: String, icon: Int, onClick: (() -> Unit)? =null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick?.invoke() }
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(title, fontSize = 18.sp, fontFamily = kumbuhFont, fontWeight = FontWeight.Bold)
            if (subtitle.isNotEmpty()) {
                Text(subtitle, fontSize = 13.sp, color = Color.Gray)
            }
        }

        Icon(
            painter = painterResource(id = icon),
            contentDescription = "icon",
            modifier = Modifier.size(22.dp)
        )
    }
}
