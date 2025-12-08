package com.example.vivochat.presentation.ui.screens.home

import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.vivochat.presentation.ui.screens.home.components.ChatHeader
import com.example.vivochat.presentation.ui.screens.home.components.ChatItem
import com.example.vivochat.presentation.ui.screens.home.components.ChatItemShimmer
import com.example.vivochat.presentation.ui.screens.home.components.HomeHeaderShimmer
import com.example.vivochat.presentation.ui.screens.home.components.StoryUploadingIndicator
import com.example.vivochat.presentation.ui.theme.Primary
import com.example.vivochat.presentation.view.home.components.HomeHeader
import com.example.vivochat.presentation.viewModel.StoryViewModel.StoryViewModel
import com.example.vivochat.presentation.viewModel.user_view_model.UserState
import com.example.vivochat.presentation.viewModel.user_view_model.UserViewModel
import com.example.vivochat.presentation.viewModel.StoryViewModel.StoryState
import com.example.vivochat.presentation.viewModel.StoryViewModel.UploadingStoryState
import com.example.vivochat.presentation.viewModel.message_viewmodel.MessageViewModel
import com.example.vivochat.presentation.viewModel.shared_view_model.SharedViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.serialization.Serializable
import java.net.URLEncoder

@Serializable
data object HomeRoute

fun NavGraphBuilder.homeScreen(
    navController: NavController,
    viewModel: UserViewModel,
    storyViewModel: StoryViewModel,
    sharedViewModel: SharedViewModel,
) {
    composable<HomeRoute> {
        Home(
            navController = navController,
            viewModel = viewModel,
            storyViewModel = storyViewModel,
            sharedViewModel = sharedViewModel
        )
    }
}
@Composable
fun Home(
    navController: NavController,
    storyViewModel: StoryViewModel,
    viewModel: UserViewModel,
    messageViewModel: MessageViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel
) {

    val context = LocalContext.current
    val state = viewModel.userDataState.collectAsState()
    val isRefreshing = remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing.value)


    val storyState = storyViewModel.storyState.collectAsState()
    val uploadingStoryState = storyViewModel.uploadingStoryState.collectAsState()
    LaunchedEffect(state.value) {
        if (state.value is UserState.UserDataSuccess) {
            storyViewModel.getAvaUsersStories(viewModel.availableContacts,viewModel.user)
            viewModel.changeStateToAllSuccess()
        }
    }

    LaunchedEffect(uploadingStoryState.value) {
        if (uploadingStoryState.value is UploadingStoryState.UploadingStorySuccess) {
            Toast.makeText(context, "Story uploaded", Toast.LENGTH_SHORT).show()
            storyViewModel.resetUploadedStoryState()
        } else if (uploadingStoryState.value is UploadingStoryState.UploadingStoryFailed) {
            Toast.makeText(context, "Failed to upload story", Toast.LENGTH_SHORT).show()
            storyViewModel.resetUploadedStoryState()
        }

    }

    if(uploadingStoryState.value == UploadingStoryState.UploadingStoryLoading){
        StoryUploadingIndicator()
    }

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = {
            viewModel.getUserData()
        },
        indicator = { s, trigger ->
        SwipeRefreshIndicator(s, trigger, contentColor = Primary)
    }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .padding(top = 40.dp),
            ) {
            if (state.value is UserState.AllSuccess && storyState.value is StoryState.StorySuccess) {

                item { HomeHeader(viewModel, navController, storyViewModel, sharedViewModel) }
                item { Spacer(Modifier.height(10.dp)) }

                item { ChatHeader() }

                item { Spacer(Modifier.height(10.dp)) }
                items(viewModel.availableContacts.size) {
                    val reciverId = viewModel.availableContacts[it].userId
                    LaunchedEffect(reciverId) {
                        messageViewModel.getLastMessage(reciverId)
                    }
                    val lastMessageMap = messageViewModel.lastMessages.collectAsState()
                    val lastMessage = lastMessageMap.value[reciverId]
                    val encodedUrl =
                        URLEncoder.encode(viewModel.availableContacts[it].imageUrl ?: "", "UTF-8")
                    ChatItem(
                        lastMessage?.message ?: "",
                        timeOfMessage = lastMessage?.date,
                        viewModel.availableContacts[it].fullName,
                        imageUrl = viewModel.availableContacts[it].imageUrl,
                        { navController.navigate("chat/${viewModel.availableContacts[it].fullName}/${reciverId}/${encodedUrl}") },
                        viewModel
                    )
                    Spacer(Modifier.height(10.dp))
                }
            } else {
                item { HomeHeaderShimmer() }
                item { Spacer(Modifier.height(10.dp)) }

                item { ChatHeader() }

                items(6) {
                    ChatItemShimmer()
                    Spacer(Modifier.height(16.dp))
                }
            }


        }

    }
}