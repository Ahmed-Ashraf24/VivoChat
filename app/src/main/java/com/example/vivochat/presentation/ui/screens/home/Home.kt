package com.example.vivochat.presentation.ui.screens.home

import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
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
import com.example.vivochat.domain.entity.User
import com.example.vivochat.presentation.ui.screens.home.components.ChatHeader
import com.example.vivochat.presentation.ui.screens.home.components.ChatItem
import com.example.vivochat.presentation.ui.screens.home.components.ChatItemShimmer
import com.example.vivochat.presentation.ui.screens.home.components.HomeHeaderShimmer
import com.example.vivochat.presentation.ui.screens.home.components.StoryUploadingIndicator
import com.example.vivochat.presentation.ui.screens.home.viewmodel.HomeViewModel
import com.example.vivochat.presentation.ui.theme.Primary
import com.example.vivochat.presentation.view.home.components.HomeHeader
import com.example.vivochat.presentation.ui.screens.home.viewmodel.UserState
import com.example.vivochat.presentation.ui.screens.story.viewmodel.StoryState
import com.example.vivochat.presentation.ui.screens.story.viewmodel.UploadingStoryState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.serialization.Serializable
import java.net.URLEncoder

@Serializable
data object HomeRoute

fun NavGraphBuilder.homeScreen(
    navController: NavController,
    navigateToReel: () -> Unit,
    onStoryClicked: (User) -> Unit,
    onChatClicked: (String, String, String) -> Unit

) {
    composable<HomeRoute> {
        Home(
            navController = navController,
            navigateToReel = navigateToReel,
            onStoryClicked = onStoryClicked,
            onChatClicked = onChatClicked

        )
    }
}
@Composable
fun Home(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToReel: () -> Unit,
    onStoryClicked:(User)->Unit,
    onChatClicked: (String, String, String) -> Unit
) {

    val context = LocalContext.current
    val state = viewModel.userDataState.collectAsState()
    val isRefreshing = remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing.value)


    val storyState = viewModel.storyState.collectAsState()
    val uploadingStoryState = viewModel.uploadingStoryState.collectAsState()
    LaunchedEffect(state.value) {
        if (state.value is UserState.UserDataSuccess) {
            viewModel.run {
                getAvaUsersStories(availableContacts,viewModel.user)
                changeStateToAllSuccess()
            }
        }
    }

    LaunchedEffect(uploadingStoryState.value) {
        if (uploadingStoryState.value is UploadingStoryState.UploadingStorySuccess) {
            Toast.makeText(context, "Story uploaded", Toast.LENGTH_SHORT).show()
            viewModel.resetUploadedStoryState()
        } else if (uploadingStoryState.value is UploadingStoryState.UploadingStoryFailed) {
            Toast.makeText(context, "Failed to upload story", Toast.LENGTH_SHORT).show()
            viewModel.resetUploadedStoryState()
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

                item { HomeHeader(viewModel, navController, onStoryClicked = onStoryClicked) }
                item { Spacer(Modifier.height(10.dp)) }

                item { ChatHeader() }

                item { Spacer(Modifier.height(10.dp)) }
                items(viewModel.availableContacts.size) {
                    val reciverId = viewModel.availableContacts[it].userId
                    LaunchedEffect(reciverId) {
                        viewModel.getLastMessage(reciverId)
                    }
                    val lastMessageMap = viewModel.lastMessages.collectAsState()
                    val lastMessage = lastMessageMap.value[reciverId]
                   ChatItem(
                        lastMessage?.message ?: "",
                        timeOfMessage = lastMessage?.date,
                        viewModel.availableContacts[it].fullName,
                        imageUrl = viewModel.availableContacts[it].imageUrl,
                        {
                            onChatClicked(viewModel.availableContacts[it].fullName,reciverId,viewModel.availableContacts[it].imageUrl?:"")
                        },
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

            item {
                androidx.compose.material3.Button(
                    onClick = navigateToReel
                ) {
                    Text("Navigate to reel")
                }
            }
        }

    }
}