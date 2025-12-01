package com.example.vivochat.presentation.ui.screens.nav

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
import com.example.vivochat.data.dataSource.local.LocalDataSource
import com.example.vivochat.domain.repository.IUserRepository
import com.example.vivochat.presentation.ui.screens.Story.StoryScreen
import com.example.vivochat.presentation.ui.screens.nav.component.BottomNavBar
import com.example.vivochat.presentation.ui.screens.setting.SettingsScreen
import com.example.vivochat.presentation.view.home.Home
import kotlinx.coroutines.launch

@Composable
fun NavScreen(
    navController: NavController,
    viewModelStoreOwner: ViewModelStoreOwner,
    userRepository: IUserRepository,
    localDataSource: LocalDataSource
) {
    val state = rememberPagerState(pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavBar(
                modifier = Modifier.padding(bottom = 30.dp),
                onTabSelected = { tabIndex ->
                    coroutineScope.launch {
                        state.animateScrollToPage(tabIndex)
                    }
                })
        }
    ) { innerPadding ->
        HorizontalPager(state = state, Modifier.padding(innerPadding)) { pageIndex ->

            when (pageIndex) {
                0 -> Home(navController = navController, viewModelStoreOwner, userRepository,localDataSource)
                1 -> StoryScreen()
                2 -> SettingsScreen()
            }

        }
    }
}