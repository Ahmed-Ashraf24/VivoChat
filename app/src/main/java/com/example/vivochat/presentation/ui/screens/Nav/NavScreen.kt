package com.example.vivochat.presentation.ui.screens.Nav

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vivochat.R
import com.example.vivochat.presentation.ui.screens.Nav.component.BottomNavBar
import com.example.vivochat.presentation.ui.screens.Nav.component.BottomNavTab
import com.example.vivochat.presentation.ui.screens.Story.StoryScreen
import com.example.vivochat.presentation.ui.screens.setting.SettingsScreen
import com.example.vivochat.presentation.ui.theme.Primary
import com.example.vivochat.presentation.view.home.Home
import kotlinx.coroutines.launch

@Composable
fun NavScreen(modifier: Modifier = Modifier) {
    val state = rememberPagerState(pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavBar(modifier=Modifier.padding(bottom = 30.dp),
                onTabSelected = {tabIndex->
                    coroutineScope.launch {
                        state.animateScrollToPage(tabIndex)
                    }
                })
        }
    ) { innerPadding ->
        HorizontalPager(state = state, Modifier.padding(innerPadding)) { pageIndex ->

            when (pageIndex) {
                0 -> Home()
                1 -> StoryScreen()
                2 -> SettingsScreen()
            }

        }
    }
}