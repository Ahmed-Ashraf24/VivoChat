package com.example.vivochat.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
import com.example.vivochat.presentation.ui.theme.VivoChatTheme
import com.example.vivochat.presentation.utility.ThemeManager
import com.example.vivochat.presentation.viewModel.shared_view_model.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var themeManager: ThemeManager
    val sharedViewModel: SharedViewModel by viewModels()
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
                        sharedViewModel = sharedViewModel,
                        navigateToReel = {
                            navController.navigate(ReelRoute)
                        }
                    )
                    reelScreen(
                        navigateBack = navController::navigateUp
                    )
                }
            }
        }
    }

}

