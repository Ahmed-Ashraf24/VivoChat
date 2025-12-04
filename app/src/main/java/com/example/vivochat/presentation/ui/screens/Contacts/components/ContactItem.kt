package com.example.vivochat.presentation.ui.screens.Contacts.components

import CircleAvatar
import android.content.Intent
import android.net.Uri
import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vivochat.domain.entity.Contact

@Composable
fun ContactItem(contact: Contact) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                try {
                    Log.d("phoneeee num", contact.phoneNum)
                    val uri = Uri.parse("smsto:${contact.phoneNum}")
                    val intent = Intent(Intent.ACTION_SENDTO, uri).apply {
                        putExtra("sms_body", "Join me on Vivo Chat! Let’s chat there. \uD83D\uDE04\n")
                    }
                    context.startActivity(intent)
                } catch (e: Exception) {
                    Log.d("Error", e.toString())
                }
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            UserAvatar()
            Column(
                verticalArrangement = Arrangement.Center,
            ) {
                Text(contact.name, fontSize = 17.sp)
                Spacer(Modifier.height(5.dp))
                Text(
                    "I'm un Available",
                    maxLines = 1,
                    fontSize = 12.sp,
                    overflow = TextOverflow.Ellipsis,
                    color = Color(0xFF9E9E9E)
                )

            }
        }
    }
}