package com.example.vivochat.presentation.view.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.vivochat.data.dataSource.firebase_remote_datasource.firebase_utility.FirebaseInstance.firebaseAuth
import com.example.vivochat.presentation.ui.screens.home.components.ChatHeader
import com.example.vivochat.presentation.ui.screens.home.components.ChatItem
import com.example.vivochat.presentation.ui.screens.home.components.ChatItemShimmer
import com.example.vivochat.presentation.ui.screens.home.components.HomeHeaderShimmer
import com.example.vivochat.presentation.view.home.components.HomeHeader
import com.example.vivochat.presentation.viewModel.StoryViewModel.StoryViewModel
import com.example.vivochat.presentation.viewModel.home_view_model.HomeState
import com.example.vivochat.presentation.viewModel.home_view_model.HomeViewModel
import com.example.vivochat.presentation.viewModel.home_view_model.StoryState
import com.example.vivochat.presentation.viewModel.message_viewmodel.MessageViewModel
import java.net.URLEncoder

@Composable
fun Home(
    navController: NavController,
    viewModel: HomeViewModel,
    storyVM: StoryViewModel=hiltViewModel(),
    messageViewModel: MessageViewModel =hiltViewModel()
) {


    val userState = viewModel.userData.collectAsState()


    val storyState = storyVM.storyState.collectAsState()

    LaunchedEffect(userState.value) {
        if (userState.value is HomeState.UserDataSuccess) {
            storyVM.getAvaUsersStories(viewModel.availableContacts)
            viewModel.resetState()
        }
    }




    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .padding(top = 40.dp),

        ) {
        if (userState.value is HomeState.Idle && storyState.value is StoryState.StorySuccess) {
            item { HomeHeader(viewModel, navController, storyVM) }
            item { Spacer(Modifier.height(10.dp)) }

            item { ChatHeader() }

            item { Spacer(Modifier.height(10.dp)) }
            items(viewModel.availableContacts.size) {
                val reciverId=viewModel.availableContacts[it].userId
                LaunchedEffect( reciverId) {
                    messageViewModel.getLastMessage(firebaseAuth.currentUser!!.uid, reciverId)
                }
                val lastMessageMap = messageViewModel.lastMessages.collectAsState()
                val lastMessage = lastMessageMap.value[reciverId]
                val encodedUrl = URLEncoder.encode(viewModel.availableContacts[it].imageUrl ?: "", "UTF-8")
                ChatItem(lastMessage?.message?:"", timeOfMessage = lastMessage?.date,
                    viewModel.availableContacts[it].fullName, imageUrl = viewModel.availableContacts[it].imageUrl,
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