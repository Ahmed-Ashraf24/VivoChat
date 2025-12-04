package com.example.vivochat.presentation.view.home.components

import CircleAvatar
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vivochat.presentation.viewModel.StoryViewModel.StoryViewModel
import com.example.vivochat.presentation.viewModel.home_view_model.HomeViewModel
import com.example.vivochat.presentation.viewModel.shared_view_model.SharedViewModel

@Composable
fun StoryItem(
    viewModel: HomeViewModel,
    storyViewModel: StoryViewModel,
    navController: NavController,
    sharedViewModel: SharedViewModel
) {

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        item { AddStoryAvatar(viewModel, storyViewModel, navController, sharedViewModel) }
        items(viewModel.availableContacts.size) {
            if (viewModel.availableContacts[it].stories!!.size > 0 && viewModel.user.userId != viewModel.availableContacts[it].userId) {
                UserStory(
                    viewModel.availableContacts[it].fullName,
                    viewModel.availableContacts[it].imageUrl,
                    {

                        sharedViewModel.sendUser(  viewModel.availableContacts[it])
                        navController.navigate("storyViewScreen")
                    }
                )
            }
        }

    }
}