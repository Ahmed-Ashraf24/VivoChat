package com.example.vivochat.presentation.ui.screens.Story

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vivochat.presentation.ui.screens.Story.component.CreateStoryItem
import com.example.vivochat.presentation.ui.screens.Story.component.StoryItem
import com.example.vivochat.presentation.ui.screens.home.components.StoryUploadingIndicator
import com.example.vivochat.presentation.ui.theme.Poppins
import com.example.vivochat.presentation.view.home.components.UserStory
import com.example.vivochat.presentation.viewModel.StoryViewModel.StoryViewModel
import com.example.vivochat.presentation.viewModel.StoryViewModel.UploadingStoryState
import com.example.vivochat.presentation.viewModel.home_view_model.HomeViewModel
import com.example.vivochat.presentation.viewModel.shared_view_model.SharedViewModel


@Composable
fun StoryScreen(
    sharedViewModel: SharedViewModel,
    homeViewModel: HomeViewModel,
    navController: NavController,
    storyViewModel: StoryViewModel
) {
    val context = LocalContext.current
    val state = storyViewModel.uploadingStoryState.collectAsState()
    LaunchedEffect(state.value) {
        if (state.value is UploadingStoryState.UploadingStorySuccess) {
            Toast.makeText(context, "Story uploaded", Toast.LENGTH_SHORT).show()
            storyViewModel.resetUploadedStoryState()
        } else if (state.value is UploadingStoryState.UploadingStoryFailed) {
            Toast.makeText(context, "Failed to upload story", Toast.LENGTH_SHORT).show()
            storyViewModel.resetUploadedStoryState()
        }

    }
    if (state.value is UploadingStoryState.UploadingStoryLoading) {
        StoryUploadingIndicator()
    }
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(vertical = 30.dp, horizontal = 10.dp)
    ) {
        item {
            CreateStoryItem(
                Modifier.padding(vertical = 10.dp),
                storyViewModel,
                homeViewModel,
                sharedViewModel,
                navController
            )
        }
        items(homeViewModel.availableContacts.size) {
            if (homeViewModel.availableContacts[it].stories!!.size > 0 && homeViewModel.user.userId != homeViewModel.availableContacts[it].userId) {
                StoryItem(
                    modifier = Modifier.padding(vertical = 10.dp),
                    hasStory = true,
                    homeViewModel.availableContacts[it],
                    {
                        sharedViewModel.sendUser(homeViewModel.availableContacts[it])
                        navController.navigate("storyViewScreen")
                    }
                )
            }
        }
    }
}