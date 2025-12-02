package com.example.vivochat.presentation.view.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import com.example.vivochat.domain.repository.IUserRepository
import com.example.vivochat.presentation.ui.screens.home.components.ChatHeader
import com.example.vivochat.presentation.ui.screens.home.components.ChatItem
import com.example.vivochat.presentation.view.home.components.HomeHeader
import com.example.vivochat.presentation.viewModel.home_view_model.HomeViewModel
import com.example.vivochat.presentation.viewModel.home_view_model.HomeViewModelFac
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Home(
    navController: NavController,
    viewModelStoreOwner: ViewModelStoreOwner,
    userRepo: IUserRepository,
    firebaseAuth: FirebaseAuth
) {
    val viewModelFac = HomeViewModelFac(userRepo,firebaseAuth)
    val viewModel =
        ViewModelProvider(viewModelStoreOwner, viewModelFac).get(HomeViewModel::class.java)
    val userList =viewModel.userList.collectAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .imePadding()
            .padding(top = 50.dp),

        ) {
        item { HomeHeader(viewModel) }
        item { Spacer(Modifier.height(10.dp)) }

        item { ChatHeader() }

        item { Spacer(Modifier.height(10.dp)) }
        items(userList.value.size) {
            ChatItem(userList.value[it].fullName,{ navController.navigate("chat/${userList.value[it].fullName}/${userList.value[it].userId}") })
            Spacer(Modifier.height(10.dp))
        }
    }

}