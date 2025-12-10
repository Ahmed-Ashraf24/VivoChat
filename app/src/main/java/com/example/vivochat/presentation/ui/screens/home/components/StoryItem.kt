package com.example.vivochat.presentation.view.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.vivochat.domain.entity.User
import com.example.vivochat.presentation.ui.screens.home.viewmodel.HomeViewModel

@Composable
fun StoryItem(
    viewModel: HomeViewModel,
    onStoryClicked: (User)->Unit
) {

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        item { AddStoryAvatar(viewModel, onStoryClicked =  onStoryClicked) }
        items(viewModel.availableContacts.size) {
            if (viewModel.availableContacts[it].stories!!.size > 0 && viewModel.user.userId != viewModel.availableContacts[it].userId) {
                UserStory(
                    viewModel.availableContacts[it].fullName,
                    viewModel.availableContacts[it].imageUrl,
                    {
                        onStoryClicked(viewModel.availableContacts[it])
                    }
                )
            }
        }

    }
}