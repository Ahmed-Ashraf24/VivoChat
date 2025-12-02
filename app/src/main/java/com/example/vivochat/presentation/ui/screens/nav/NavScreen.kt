package com.example.vivochat.presentation.ui.screens.nav

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import com.example.vivochat.domain.repository.IUserRepository
import com.example.vivochat.presentation.ui.screens.Story.StoryScreen
import com.example.vivochat.presentation.ui.screens.nav.component.BottomNavBar
import com.example.vivochat.presentation.ui.screens.setting.SettingsScreen
import com.example.vivochat.presentation.ui.theme.Primary
import com.example.vivochat.presentation.view.home.Home
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavScreen(
    navController: NavController,
    viewModelStoreOwner: ViewModelStoreOwner,
    userRepository: IUserRepository,
    firebaseAuth: FirebaseAuth
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
        HorizontalPager(state = state) { pageIndex ->

            when (pageIndex) {
                0 -> Home(
                    navController = navController,
                    viewModelStoreOwner,
                    userRepository,
                    firebaseAuth
                )
                1 -> StoryScreen()
                2 -> SettingsScreen()
            }

        }
    }
}