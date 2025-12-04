package com.example.vivochat.presentation.ui.screens.setting.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.vivochat.R
import com.example.vivochat.domain.entity.User
import com.example.vivochat.presentation.ui.theme.kumbuhFont

@Composable
fun ProfileSection(userName:String,userImageUrl:String?,userEmail:String) {
    Row(verticalAlignment = Alignment.CenterVertically) {

        AsyncImage(
            model =userImageUrl,
            contentDescription = "profile",
            modifier = Modifier
                .size(75.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(userName, fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold, fontFamily = kumbuhFont)

            Text(userEmail, fontSize = 15.sp, color = Color.Gray,
                fontFamily = kumbuhFont , fontWeight = FontWeight.ExtraBold)
        }
    }
}

