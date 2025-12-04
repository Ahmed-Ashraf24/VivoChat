package com.example.vivochat.presentation.view.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import com.example.vivochat.data.dataSource.firebase_remote_datasource.FirebaseRemoteDataSource
import com.example.vivochat.data.repository.MessageRepository
import com.example.vivochat.domain.entity.User
import com.example.vivochat.domain.repository.IMediaRepository
import com.example.vivochat.domain.repository.IUserRepository
import com.example.vivochat.presentation.ui.screens.home.components.ChatHeader
import com.example.vivochat.presentation.ui.screens.home.components.ChatItem
import com.example.vivochat.presentation.view.home.components.HomeHeader
import com.example.vivochat.presentation.viewModel.MessageViewModel
import com.example.vivochat.presentation.viewModel.home_view_model.HomeState
import com.example.vivochat.presentation.viewModel.home_view_model.HomeViewModel
import com.example.vivochat.presentation.viewModel.home_view_model.HomeViewModelFac
import com.google.firebase.auth.FirebaseAuth
import java.net.URLEncoder

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun Home(
    mediaRepo: IMediaRepository,
    navController: NavController,
    viewModelStoreOwner: ViewModelStoreOwner,
    userRepo: IUserRepository,
    firebaseAuth: FirebaseAuth
) {

    val viewModelFac = HomeViewModelFac(mediaRepo ,userRepo, firebaseAuth, LocalContext.current)
    val viewModel =
        ViewModelProvider(viewModelStoreOwner, viewModelFac).get(HomeViewModel::class.java)
    val messageViewModel= MessageViewModel(MessageRepository(FirebaseRemoteDataSource()))

    val state = viewModel.userData.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .imePadding()
            .padding(top = 40.dp),

        ) {
        if (state.value is HomeState.DataSuccess) {
         item { HomeHeader(viewModel, navController) }
        item { Spacer(Modifier.height(10.dp)) }

        item { ChatHeader() }

        item { Spacer(Modifier.height(10.dp)) }
        if (state.value is HomeState.DataSuccess) {
            items(viewModel.availableContacts.size) {
                val reciverId=viewModel.availableContacts[it].userId
                LaunchedEffect( reciverId) {
                    messageViewModel.getLastMessage(firebaseAuth.currentUser!!.uid, reciverId)
                }
                val lastMessageMap = messageViewModel.lastMessages.collectAsState()
                val lastMessage = lastMessageMap.value[reciverId]
                val encodedUrl = URLEncoder.encode(viewModel.availableContacts[it].imageUrl ?: "", "UTF-8")
                Log.d("avilable contact data",viewModel.availableContacts.toString())
                ChatItem(lastMessage?.message?:"",
                    viewModel.availableContacts[it].fullName, imageUrl = viewModel.availableContacts[it].imageUrl,
                    { navController.navigate("chat/${viewModel.availableContacts[it].fullName}/${reciverId}/${encodedUrl}") },
                    viewModel
                )
                Spacer(Modifier.height(10.dp))
            }
        } else {

            item { Text("Loading") }
        }

    }

}

}