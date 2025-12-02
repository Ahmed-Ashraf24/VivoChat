package com.example.vivochat.presentation.view.home

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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import com.example.vivochat.domain.entity.User
import com.example.vivochat.domain.repository.IUserRepository
import com.example.vivochat.presentation.ui.screens.home.components.ChatHeader
import com.example.vivochat.presentation.ui.screens.home.components.ChatItem
import com.example.vivochat.presentation.view.home.components.HomeHeader
import com.example.vivochat.presentation.viewModel.home_view_model.HomeState
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

    val viewModelFac = HomeViewModelFac(userRepo, firebaseAuth, LocalContext.current)
    val viewModel =
        ViewModelProvider(viewModelStoreOwner, viewModelFac).get(HomeViewModel::class.java)

    val state = viewModel.userData.collectAsState()

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
        if (state.value is HomeState.DataSuccess) {
            items(viewModel.availableContacts.size) {
                ChatItem(viewModel.availableContacts[it].fullName,
                    { navController.navigate("chat/${viewModel.availableContacts[it].fullName}/${viewModel.availableContacts[it].userId}") })
                Spacer(Modifier.height(10.dp))
            }
        } else {
            item { Text("Loading") }
        }

        item { Text("All contacts") }

        if (state.value is HomeState.DataSuccess) {
            items(viewModel.unAvailableContacts.size) {
                ChatItem(viewModel.unAvailableContacts[it].name, { navController.navigate("chat") })
                Spacer(Modifier.height(10.dp))
            }
        } else {
            item { Text("Loading") }
        }
    }

}