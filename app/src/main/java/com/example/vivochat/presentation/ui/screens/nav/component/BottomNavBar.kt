package com.example.vivochat.presentation.ui.screens.nav.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.vivochat.R
import com.example.vivochat.presentation.ui.theme.Primary

@Composable
fun BottomNavBar(modifier: Modifier = Modifier,onTabSelected:(Int)->Unit) {
    var selectedTab by remember { mutableStateOf(0) }

    TabRow(
        containerColor = Color.White,
        selectedTabIndex = selectedTab,
        modifier = modifier
            .height(70.dp)
            .fillMaxWidth(),
        indicator = {},
        divider = {}
    ) {
        BottomNavTab(
            icon = painterResource(R.drawable.outlined_home_ic),
            selectedIcon = painterResource(R.drawable.filled_home_ic),
            name = "home",
            selected = selectedTab == 0,
            iconColor = Primary,
            selectedIconColor = Primary,
            onClick = {
                selectedTab = 0
               onTabSelected(selectedTab)
            }
        )
        BottomNavTab(
            icon = painterResource(R.drawable.outlined_story_ic),
            selectedIcon = painterResource(R.drawable.filled_story_ic),
            name = "story",
            selected = selectedTab == 1,
            iconColor = Primary,
            selectedIconColor = Primary,
            onClick = {
                selectedTab = 1
                onTabSelected(selectedTab)
            }
        )
        BottomNavTab(
            icon = painterResource(R.drawable.outlined_setting_ic),
            selectedIcon = painterResource(R.drawable.filled_sitings_ic),
            name = "settings",
            selected = selectedTab == 2,
            iconColor = Primary,
            selectedIconColor = Primary,
            onClick = {
                selectedTab = 2
                onTabSelected(selectedTab)
            }
        )


    }
}