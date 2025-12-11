package com.example.vivochat.presentation.ui.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.vivochat.domain.entity.Contact
import com.example.vivochat.presentation.ui.screens.Contacts.Contacts
import com.example.vivochat.presentation.ui.screens.Contacts.ContactsRoute
import com.example.vivochat.presentation.ui.screens.home.HomeRoute
import com.example.vivochat.presentation.ui.screens.home.homeScreen
import com.example.vivochat.presentation.ui.screens.main.component.BottomNavBar
import com.example.vivochat.presentation.ui.screens.setting.settingsScreen
import com.example.vivochat.presentation.ui.screens.story.storyScreen
import com.example.vivochat.presentation.utility.NavigationAction
import kotlinx.serialization.Serializable

@Serializable
data object MainRoute

fun NavGraphBuilder.mainScreen(
    onNavigation: (navigationAction: NavigationAction) -> Unit
) {
    composable<MainRoute> {
        val mainController = rememberMainNavController()
        val currentBottomNav = mainController.currentBottomNav
        Scaffold(
            bottomBar = {
                BottomNavBar(
                    selectedTab = currentBottomNav,
                    onTabSelected = { mainController.navigateToScreen(it.route) }
                )
            }
        ) { innerPadding ->
            NavHost(
                modifier = Modifier.padding(innerPadding),
                navController = mainController.navController,
                startDestination = HomeRoute
            ) {
                homeScreen(
                    navController = mainController.navController,
                    onNavigation = onNavigation
                )
                storyScreen(
                    onNavigation = onNavigation
                )
                settingsScreen(
                    onNavigation = onNavigation
                )
                composable<ContactsRoute> { navBackStackEntry ->
                    val unAvailableContacts =
                        navBackStackEntry.savedStateHandle.get<List<Contact>>("unAvailableContacts")

                    Contacts(unAvailableContacts!!)
                }


            }
        }
    }
}