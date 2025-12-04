package com.example.vivochat.presentation.view.home.components

import CircleAvatar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vivochat.presentation.viewModel.home_view_model.HomeViewModel

@Composable
fun StoryItem(viewModel: HomeViewModel) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        item { AddStoryAvatar(viewModel) }
        items(viewModel.availableContacts.size) {
            if(viewModel.availableContacts[it].userId!=viewModel.user.userId){
                UserStory(viewModel.availableContacts[it].fullName,viewModel.availableContacts[it].imageUrl)
            }
        }

    }
}