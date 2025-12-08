package com.example.vivochat.presentation.ui.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.vivochat.domain.entity.Contact
import com.example.vivochat.presentation.ui.screens.Contacts.Contacts
import com.example.vivochat.presentation.ui.screens.Contacts.ContactsRoute
import com.example.vivochat.presentation.ui.screens.chat.ChatScreen
import com.example.vivochat.presentation.ui.screens.home.HomeRoute
import com.example.vivochat.presentation.ui.screens.home.homeScreen
import com.example.vivochat.presentation.ui.screens.nav.component.BottomNavBar
import com.example.vivochat.presentation.ui.screens.setting.settingsScreen
import com.example.vivochat.presentation.ui.screens.story.storyScreen
import com.example.vivochat.presentation.ui.screens.story.storyViewScreen
import com.example.vivochat.presentation.viewModel.StoryViewModel.StoryViewModel
import com.example.vivochat.presentation.viewModel.shared_view_model.SharedViewModel
import com.example.vivochat.presentation.viewModel.user_view_model.UserViewModel
import kotlinx.serialization.Serializable
import java.net.URLDecoder

@Serializable
data object MainRoute

fun NavGraphBuilder.mainScreen(
    sharedViewModel: SharedViewModel,
    navigateToReel: () -> Unit
) {
    composable<MainRoute> {
        val mainController = rememberMainNavController()
        val currentBottomNav = mainController.currentBottomNav
        val userViewModel: UserViewModel = hiltViewModel()
        val storyViewModel: StoryViewModel = hiltViewModel()
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
                    viewModel = userViewModel,
                    storyViewModel = storyViewModel,
                    sharedViewModel = sharedViewModel,
                    navigateToReel = navigateToReel
                )
                storyScreen(
                    mainController.navController,
                    userViewModel = userViewModel,
                    storyViewModel = storyViewModel,
                    sharedViewModel = sharedViewModel
                )
                settingsScreen(
                    mainController.navController,
                    loggedUser = userViewModel.user
                )
                storyViewScreen()
                composable<ContactsRoute> { navBackStackEntry ->
                    val unAvailableContacts =
                        navBackStackEntry.savedStateHandle.get<List<Contact>>("unAvailableContacts")

                    Contacts(unAvailableContacts!!)
                }
                composable(
                    route = "chat/{userName}/{userId}/{userImageUrl}",
                    arguments = listOf(
                        navArgument("userName") { type = NavType.StringType },
                        navArgument("userId") { type = NavType.StringType },
                        navArgument("userImageUrl") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val userId = backStackEntry.arguments?.getString("userId")!!
                    val userName = backStackEntry.arguments?.getString("userName")!!
                    val userImageUrl = URLDecoder.decode(
                        backStackEntry.arguments?.getString("userImageUrl")!!,
                        "UTF-8"
                    )
                    ChatScreen(
                        navController = mainController.navController,
                        reciverName = userName,
                        reciverImageUrl = userImageUrl,
                        reciverId = userId
                    )

                }
            }
        }
    }
}