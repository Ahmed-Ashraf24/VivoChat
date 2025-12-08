package com.example.vivochat.presentation.ui.screens.story

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.vivochat.presentation.ui.screens.home.Home
import com.example.vivochat.presentation.ui.screens.story.component.CreateStoryItem
import com.example.vivochat.presentation.ui.screens.story.component.StoryItem
import com.example.vivochat.presentation.ui.screens.home.components.StoryUploadingIndicator
import com.example.vivochat.presentation.ui.theme.Poppins
import com.example.vivochat.presentation.viewModel.StoryViewModel.StoryViewModel
import com.example.vivochat.presentation.viewModel.StoryViewModel.UploadingStoryState
import com.example.vivochat.presentation.viewModel.user_view_model.UserViewModel
import com.example.vivochat.presentation.viewModel.shared_view_model.SharedViewModel
import kotlinx.serialization.Serializable

@Serializable
data object StoryRoute

fun NavGraphBuilder.storyScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    storyViewModel: StoryViewModel,
    sharedViewModel: SharedViewModel,
) {
    composable<StoryRoute> {
        StoryScreen(
            navController = navController,
            storyViewModel = storyViewModel,
            userViewModel = userViewModel,
            sharedViewModel = sharedViewModel
        )
    }
}

@Composable
fun StoryScreen(
    sharedViewModel: SharedViewModel,
    userViewModel: UserViewModel,
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
            Text("Story", fontSize = 25.sp, fontFamily = Poppins)
            CreateStoryItem(
                Modifier.padding(vertical = 10.dp),
                storyViewModel,
                userViewModel,
                sharedViewModel,
                navController
            )
        }
        items(userViewModel.availableContacts.size) {
            if (userViewModel.availableContacts[it].stories!!.size > 0 && userViewModel.user.userId != userViewModel.availableContacts[it].userId) {
                StoryItem(
                    modifier = Modifier.padding(vertical = 10.dp),
                    hasStory = true,
                    userViewModel.availableContacts[it],
                    {
                        sharedViewModel.sendUser(userViewModel.availableContacts[it])
                        navController.navigate("storyViewScreen")
                    }
                )
            }
        }
    }
}