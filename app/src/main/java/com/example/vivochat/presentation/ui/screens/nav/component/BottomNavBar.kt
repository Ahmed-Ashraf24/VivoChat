package com.example.vivochat.presentation.ui.screens.nav.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.vivochat.presentation.ui.screens.main.BottomNav
import com.example.vivochat.presentation.ui.theme.Primary

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    selectedTab: BottomNav?,
    onTabSelected: (BottomNav) -> Unit,
) {
    TabRow(
        selectedTabIndex = selectedTab?.ordinal ?: 0,
        modifier = modifier
            .navigationBarsPadding()
            .fillMaxWidth(),
        indicator = {},
        divider = {}
    ) {
        BottomNav.entries.forEach {
            BottomNavTab(
                icon = painterResource(it.icon),
                selectedIcon = painterResource(it.selectedIcon),
                name = it.title,
                selected = selectedTab == it,
                iconColor = Primary,
                selectedIconColor = Primary,
                onClick = { onTabSelected(it) }
            )
        }
    }
}