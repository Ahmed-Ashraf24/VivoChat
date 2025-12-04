package com.example.vivochat.presentation.ui.screens.nav

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.vivochat.presentation.ui.screens.Story.StoryScreen
import com.example.vivochat.presentation.ui.screens.nav.component.BottomNavBar
import com.example.vivochat.presentation.ui.screens.setting.SettingsScreen
import com.example.vivochat.presentation.ui.theme.Primary
import com.example.vivochat.presentation.view.home.Home
import com.example.vivochat.presentation.viewModel.StoryViewModel.StoryViewModel
import com.example.vivochat.presentation.viewModel.user_view_model.UserViewModel
import com.example.vivochat.presentation.viewModel.shared_view_model.SharedViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavScreen(
    navController: NavController,
    userViewModel: UserViewModel = hiltViewModel(),
    sharedViewModel : SharedViewModel,
    storyViewModel: StoryViewModel=hiltViewModel()
) {
    val state = rememberPagerState(pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        Modifier
            .fillMaxSize()
            .background(color = Primary),
        bottomBar = {
            BottomNavBar(
                onTabSelected = { tabIndex ->
                    coroutineScope.launch {
                        state.animateScrollToPage(tabIndex)
                    }
                })
        }
    ) {
        HorizontalPager(state = state,userScrollEnabled = false) { pageIndex ->

            when (pageIndex) {
                0 -> Home(
                    navController = navController,
                    sharedViewModel = sharedViewModel,
                    viewModel = userViewModel,
                    storyViewModel = storyViewModel
                )
                1 -> StoryScreen(sharedViewModel,userViewModel,navController,storyViewModel)
                2 -> SettingsScreen(navController, loggedUser = userViewModel.user)
            }

        }
    }
}