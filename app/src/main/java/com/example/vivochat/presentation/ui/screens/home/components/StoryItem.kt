package com.example.vivochat.presentation.view.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vivochat.presentation.ui.screens.story.StoryViewRoute
import com.example.vivochat.presentation.viewModel.StoryViewModel.StoryViewModel
import com.example.vivochat.presentation.viewModel.shared_view_model.SharedViewModel
import com.example.vivochat.presentation.viewModel.user_view_model.UserViewModel

@Composable
fun StoryItem(
    viewModel: UserViewModel,
    storyViewModel: StoryViewModel,
    navController: NavController,
    sharedViewModel: SharedViewModel
) {

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        item { AddStoryAvatar(viewModel, storyViewModel, navController = navController, sharedViewModel =  sharedViewModel) }
        items(viewModel.availableContacts.size) {
            if (viewModel.availableContacts[it].stories!!.size > 0 && viewModel.user.userId != viewModel.availableContacts[it].userId) {
                UserStory(
                    viewModel.availableContacts[it].fullName,
                    viewModel.availableContacts[it].imageUrl,
                    {

                        sharedViewModel.sendUser(  viewModel.availableContacts[it])
                        navController.navigate(StoryViewRoute(user = viewModel.availableContacts[it]))
                    }
                )
            }
        }

    }
}