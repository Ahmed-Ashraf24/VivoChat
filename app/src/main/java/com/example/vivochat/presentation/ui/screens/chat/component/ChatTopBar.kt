package com.example.vivochat.presentation.ui.screens.chat.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vivochat.R
import com.example.vivochat.presentation.ui.theme.Poppins
import com.example.vivochat.presentation.ui.theme.onlineColor

@Composable
fun ChatTopBar(modifier: Modifier = Modifier,onBackClicked:()->Unit) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(R.drawable.outline_arrow_back_ios_24),
            contentDescription = "back",
            modifier = Modifier.size(24.dp).clickable{onBackClicked()},
            tint = Color.Gray
        )
        Image(
            painter = painterResource(R.drawable.image),
            contentDescription = "profile",
            modifier = Modifier
                .size(35.dp)
                .clip(RoundedCornerShape(50.dp)),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .align(Alignment.Top)
                .padding(horizontal = 8.dp)
        ) {
            Text("user name ", fontSize = 18.sp, fontFamily = Poppins)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Active", fontSize = 10.sp, fontFamily = Poppins, color = Color.Gray, modifier = Modifier.padding(end = 5.dp))
                Surface(
                    Modifier.size(5.dp),
                    color = onlineColor,
                    shape = RoundedCornerShape(50.dp)
                ) {}
            }
        }

    }
}