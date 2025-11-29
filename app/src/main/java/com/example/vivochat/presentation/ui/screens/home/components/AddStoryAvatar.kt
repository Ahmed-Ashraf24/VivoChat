package com.example.vivochat.presentation.view.home.components

import CircleAvatar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vivochat.R

@Composable
fun AddStoryAvatar() {

    Column(
        modifier = Modifier.padding(start = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            CircleAvatar()
            IconButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = (15).dp, y = (15).dp),
                onClick = {}) {
                Icon(
                    painter = painterResource(R.drawable.add),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }

        }
        Spacer(Modifier.height(4.dp))
        Text(
            "Your Story",
            maxLines = 1,
            fontSize = 10.sp,
            overflow = TextOverflow.Ellipsis
        )
    }
}