package com.example.vivochat.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vivochat.domain.entity.Contact
import com.example.vivochat.presentation.ui.screens.Contacts.Contacts
import com.example.vivochat.presentation.ui.screens.splash.SplashScreen
import com.example.vivochat.presentation.ui.screens.story.StoryView
import com.example.vivochat.presentation.ui.screens.chat.ChatScreen
import com.example.vivochat.presentation.ui.screens.login.Login
import com.example.vivochat.presentation.ui.screens.nav.NavScreen
import com.example.vivochat.presentation.ui.screens.profile_image.ProfileImageScreen
import com.example.vivochat.presentation.ui.theme.VivoChatTheme
import com.example.vivochat.presentation.utility.ThemeManager
import com.example.vivochat.presentation.viewModel.shared_view_model.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLDecoder
import javax.inject.Inject
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var themeManager: ThemeManager
    val sharedViewModel : SharedViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

           val navController = rememberNavController()
            val isDark by themeManager.isDarkMode.collectAsState()
            VivoChatTheme(userPreferenceDarkTheme = isDark) {

            NavHost(navController, "splash") {
                composable("splash") {
                    SplashScreen(
                        navController,
                    )
                }

                composable("profileImageScreen") {
                    ProfileImageScreen(
                        navController=navController
                    )
                }
                composable("login") { Login(navController= navController) }
                composable("signup") {
                    SignupScreen(
                        navController= navController
                    )
                }
                composable("navScreen") {
                    NavScreen(
                        navController,
                        sharedViewModel = sharedViewModel
                    )
                }
                composable("contacts") {navBackStackEntry->
                    val unAvailableContacts =navBackStackEntry.savedStateHandle.get<List<Contact>>("unAvailableContacts")

                    Contacts(unAvailableContacts!!)
                }
                composable(
                    "chat/{userName}/{userId}/{userImageUrl}",
                    arguments = listOf(
                        navArgument("userName") { type = NavType.StringType },
                        navArgument("userId") { type = NavType.StringType },
                        navArgument("userImageUrl") { type = NavType.StringType }


                    )) { backStackEntry ->
                    val userId = backStackEntry.arguments?.getString("userId")!!
                    val userName = backStackEntry.arguments?.getString("userName")!!
                    val userImageUrl = URLDecoder.decode(backStackEntry.arguments?.getString("userImageUrl")!!, "UTF-8")
                    ChatScreen(navController = navController, reciverName = userName, reciverImageUrl = userImageUrl, reciverId = userId)

                }
                composable("storyViewScreen") {
                    StoryView(
                        sharedViewModel
                    )
                }


            }


        }
    }
}

}

