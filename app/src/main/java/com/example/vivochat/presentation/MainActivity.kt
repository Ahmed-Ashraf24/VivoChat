package com.example.vivochat.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vivochat.presentation.ui.screens.chat.chatScreen
import com.example.vivochat.presentation.ui.screens.chat.ChatRoute
import com.example.vivochat.presentation.ui.screens.splash.SplashScreen
import com.example.vivochat.presentation.ui.screens.login.Login
import com.example.vivochat.presentation.ui.screens.login.LoginRoute
import com.example.vivochat.presentation.ui.screens.main.mainScreen
import com.example.vivochat.presentation.ui.screens.profile_image.ProfileImageRoute
import com.example.vivochat.presentation.ui.screens.profile_image.ProfileImageScreen
import com.example.vivochat.presentation.ui.screens.reel.ReelRoute
import com.example.vivochat.presentation.ui.screens.reel.reelScreen
import com.example.vivochat.presentation.ui.screens.signup.SignupRoute
import com.example.vivochat.presentation.ui.screens.signup.SignupScreen
import com.example.vivochat.presentation.ui.screens.splash.SplashRoute
import com.example.vivochat.presentation.ui.screens.story.StoryViewRoute
import com.example.vivochat.presentation.ui.screens.story.storyView
import com.example.vivochat.presentation.ui.theme.VivoChatTheme
import com.example.vivochat.presentation.utility.ThemeManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var themeManager: ThemeManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()
            val isDark by themeManager.isDarkMode.collectAsState()
            VivoChatTheme(userPreferenceDarkTheme = isDark) {

                NavHost(
                    navController = navController,
                    startDestination = SplashRoute
                ) {
                    composable<SplashRoute> {
                        SplashScreen(
                            navController,
                        )
                    }

                    composable<ProfileImageRoute> {
                        ProfileImageScreen(
                            navController = navController
                        )
                    }
                    composable<LoginRoute> {
                        Login(navController = navController)
                    }
                    composable<SignupRoute> {
                        SignupScreen(
                            navController = navController
                        )
                    }

                    mainScreen(
                        navigateToReel = {
                            navController.navigate(ReelRoute)
                        },
                        navigateToStory = { navController.navigate(StoryViewRoute(it)) },
                        navigateToLogin = {
                            navController.navigate(LoginRoute) {
                                popUpTo(0) { inclusive = true }
                            }
                        },
                        navigateToChat = {userName, userId, userImageUrl ->
                        navController.navigate(ChatRoute(
                            fullName = userName,
                            reciverId = userId,
                            imageUrl = userImageUrl
                        ))
                        },
                    )
                  chatScreen(navController = navController)
                    reelScreen(
                        navigateBack = navController::navigateUp
                    )
                    storyView()
                }
            }
        }
    }

}

