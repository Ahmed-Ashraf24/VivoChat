package com.example.vivochat.presentation.ui.screens.story.component
import CircleAvatar
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.vivochat.domain.entity.User
import com.example.vivochat.presentation.ui.theme.Poppins
import com.example.vivochat.presentation.ui.theme.Primary


@Composable
fun StoryItem(modifier: Modifier = Modifier, hasStory: Boolean, user: User,onClick:()->Unit) {
    Box(
        modifier = Modifier.clickable{
            onClick()
        }
    ){
        Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Surface(
                shape = RoundedCornerShape(50.dp), modifier = Modifier.border(
                    width = 3.dp,
                    color = if (hasStory) Primary.copy(alpha = .6f) else Color.Gray.copy(alpha = .6f),
                    shape = RoundedCornerShape(50.dp)
                )
            ) {

                CircleAvatar(user.imageUrl)

            }
            Column(Modifier.padding(horizontal = 10.dp)) {
                Text(user.fullName, fontFamily = Poppins)

            }
        }
    }
}

