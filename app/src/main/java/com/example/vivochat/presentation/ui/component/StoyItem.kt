package com.example.vivochat.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vivochat.R
import com.example.vivochat.presentation.ui.theme.Poppins
import com.example.vivochat.presentation.ui.theme.Primary
import com.example.vivochat.presentation.ui.theme.onlineColor


@Composable
fun StoryItem(modifier: Modifier = Modifier,hasStory: Boolean) {
    Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Surface(shape = RoundedCornerShape(50.dp), modifier = Modifier.border(  width = 3.dp,
            color = if(hasStory)Primary.copy(alpha = .6f) else Color.Gray.copy(alpha = .6f),
            shape = RoundedCornerShape(50.dp) )) {
        Image(
            painter = painterResource(R.drawable.image),
            "profileImage", modifier = Modifier.size(50.dp), contentScale = ContentScale.FillBounds)

        }
        Column(Modifier.padding(horizontal = 10.dp)) {
            Text("user name", fontFamily = Poppins)
            Text("Yesterday", color = Color.Gray.copy(alpha = .5f), fontFamily = Poppins)
        }
    }
}

@Preview
@Composable
private fun StoryItemPreview() {
    StoryItem(hasStory = true)
}