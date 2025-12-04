package com.example.vivochat.presentation.ui.screens.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

@Composable
fun StoryCircleShimmer() {
    Box(
        modifier = Modifier
            .size(65.dp)
            .placeholder(
                visible = true,
                highlight = PlaceholderHighlight.shimmer(),
                shape = RoundedCornerShape(100.dp)
            )
    )
}
