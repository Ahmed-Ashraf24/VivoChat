package com.example.vivochat.presentation.view.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.vivochat.presentation.ui.screens.home.viewmodel.HomeViewModel
import com.example.vivochat.presentation.utility.NavigationAction

@Composable
fun StoryItem(
    viewModel: HomeViewModel,
    onNavigation: (navigationAction: NavigationAction) -> Unit
) {

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        item { AddStoryAvatar(viewModel, onNavigation) }
        items(viewModel.availableContacts.size) {
            if (viewModel.availableContacts[it].stories!!.size > 0 && viewModel.user.userId != viewModel.availableContacts[it].userId) {
                UserStory(
                    viewModel.availableContacts[it].fullName,
                    viewModel.availableContacts[it].imageUrl,
                    {
                        onNavigation(
                            NavigationAction
                                .StoryNavigation(viewModel.availableContacts[it])
                        )
                    }
                )
            }
        }

    }
}