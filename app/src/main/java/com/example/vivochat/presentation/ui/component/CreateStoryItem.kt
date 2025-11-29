package com.example.vivochat.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import com.example.vivochat.presentation.ui.theme.onlineColor

@Preview
@Composable
fun CreateStoryItem(modifier: Modifier = Modifier) {
    Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Box {
            Surface(shape = RoundedCornerShape(50.dp)) {

                Image(
                    painter = painterResource(R.drawable.image),
                    "profileImage",
                    modifier = Modifier.size(50.dp),
                    contentScale = ContentScale.FillBounds
                )

            }
            Surface(
                modifier = Modifier
                    .size(18.dp)
                    .align(Alignment.BottomEnd),
                shape = RoundedCornerShape(50.dp),
                color = onlineColor
            ) {
                Icon(
                    painter = painterResource(R.drawable.outline_add_24),
                    "add",
                    Modifier.padding(2.dp)
                )
            }

        }


        Column(Modifier
            .padding(horizontal = 10.dp)
            .weight(1f)) {
            Text("Add Story", fontFamily = Poppins)
            Text(
                "Disappears after 24 hours",
                color = Color.Gray.copy(alpha = .5f),
                fontFamily = Poppins
            )
        }
    }
}
