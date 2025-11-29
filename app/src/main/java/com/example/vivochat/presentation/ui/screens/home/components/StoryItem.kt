package com.example.vivochat.presentation.view.home.components

import CircleAvatar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StoryItem() {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        item { AddStoryAvatar() }
        items(12) {
            UserStory()

        }

    }
}