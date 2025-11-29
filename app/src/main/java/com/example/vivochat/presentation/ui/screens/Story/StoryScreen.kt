package com.example.vivochat.presentation.ui.screens.Story

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vivochat.presentation.ui.screens.Story.component.CreateStoryItem
import com.example.vivochat.presentation.ui.screens.Story.component.StoryItem
import com.example.vivochat.presentation.ui.theme.Poppins

@Preview
@Composable
fun StoryScreen() {
    Column(Modifier
        .fillMaxSize()
        .padding(vertical = 30.dp, horizontal = 10.dp)){
        Text("Story", fontSize = 25.sp, fontFamily = Poppins)
        CreateStoryItem(Modifier.padding(vertical = 10.dp))
        Text("Recent Updates", fontSize = 18.sp, color = Color.Gray.copy(alpha = .6f))
        StoryItem(modifier = Modifier.padding(vertical = 10.dp),hasStory = true)
        StoryItem(modifier = Modifier.padding(vertical = 10.dp),hasStory = false)
        StoryItem(modifier = Modifier.padding(vertical = 10.dp),hasStory = true)
        StoryItem(modifier = Modifier.padding(vertical = 10.dp),hasStory = true)
        StoryItem(modifier = Modifier.padding(vertical = 10.dp),hasStory = true)
    }
}