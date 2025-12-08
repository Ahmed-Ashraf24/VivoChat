package com.example.vivochat.presentation.ui.screens.main

import androidx.annotation.DrawableRes
import com.example.vivochat.R
import com.example.vivochat.presentation.ui.screens.home.HomeRoute
import com.example.vivochat.presentation.ui.screens.setting.SettingsRoute
import com.example.vivochat.presentation.ui.screens.story.StoryRoute

enum class BottomNav(
    @DrawableRes val icon: Int,
    @DrawableRes val selectedIcon: Int,
    val title: String,
    val route: Any
) {
    HOME(
        icon = R.drawable.outlined_home_ic,
        selectedIcon = R.drawable.filled_home_ic,
        title = "Home",
        route = HomeRoute
    ),
    STORY(
        icon = R.drawable.outlined_story_ic,
        selectedIcon = R.drawable.filled_story_ic,
        title = "Story",
        route = StoryRoute
    ),
    SETTINGS(
        icon = R.drawable.outlined_setting_ic,
        selectedIcon = R.drawable.filled_sitings_ic,
        title = "Settings",
        route = SettingsRoute
    ),
}