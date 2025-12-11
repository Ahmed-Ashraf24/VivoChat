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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.vivochat.presentation.ui.screens.story.component.CreateStoryItem
import com.example.vivochat.presentation.ui.screens.story.component.StoryItem
import com.example.vivochat.presentation.ui.screens.home.components.StoryUploadingIndicator
import com.example.vivochat.presentation.ui.theme.Poppins
import com.example.vivochat.presentation.ui.screens.story.viewmodel.StoryState
import com.example.vivochat.presentation.ui.screens.story.viewmodel.StoryViewModel
import com.example.vivochat.presentation.ui.screens.story.viewmodel.UploadingStoryState
import com.example.vivochat.presentation.utility.NavigationAction
import kotlinx.serialization.Serializable

@Serializable
data object StoryRoute

fun NavGraphBuilder.storyScreen(
    onNavigation:(NavigationAction)->Unit
) {
    composable<StoryRoute> {
        StoryScreen(
            onNavigation = onNavigation
        )
    }
}

@Composable
fun StoryScreen(
    storyViewModel: StoryViewModel= hiltViewModel(),
    onNavigation:(NavigationAction)->Unit
) {
    val context = LocalContext.current
    val uploadState = storyViewModel.uploadingStoryState.collectAsState()
    val storyState = storyViewModel.storyState.collectAsState()

    val availableContacts = storyViewModel.availableContacts.collectAsState()
    val currentUser=storyViewModel.userData.collectAsState()
    LaunchedEffect(uploadState.value) {
        if (uploadState.value is UploadingStoryState.UploadingStorySuccess) {
            Toast.makeText(context, "Story uploaded", Toast.LENGTH_SHORT).show()
            storyViewModel.resetUploadedStoryState()
        } else if (uploadState.value is UploadingStoryState.UploadingStoryFailed) {
            Toast.makeText(context, "Failed to upload story", Toast.LENGTH_SHORT).show()
            storyViewModel.resetUploadedStoryState()
        }

    }
    if (uploadState.value is UploadingStoryState.UploadingStoryLoading) {
        StoryUploadingIndicator()
    }
    if (storyState.value is StoryState.StorySuccess){
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
                onNavigation
            )
        }
        items(availableContacts.value.size) {
            if (availableContacts.value[it].stories?.isNotEmpty()==true && currentUser.value.userId != availableContacts.value[it].userId) {
                StoryItem(
                    modifier = Modifier.padding(vertical = 10.dp),
                    hasStory = true,
                    availableContacts.value[it],
                    {
                        onNavigation(NavigationAction.StoryNavigation(availableContacts.value[it]))
                    }
                )
            }
        }
    }
    }
}