package com.example.vivochat.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vivochat.data.dataSource.firebase_remote_datasource.FirebaseRemoteDataSource
import com.example.vivochat.data.dataSource.RemoteDataSource

import com.example.vivochat.data.repository.UserRepository
import com.example.vivochat.domain.repository.IUserRepository
import com.example.vivochat.presentation.ui.screens.Splash.SplashScreen
import com.example.vivochat.presentation.ui.screens.chat.ChatScreen
import com.example.vivochat.presentation.ui.screens.login.Login
import com.example.vivochat.presentation.ui.screens.nav.NavScreen
import com.example.vivochat.presentation.ui.screens.profile_image.ProfileImageScreen
import com.example.vivochat.presentation.ui.theme.VivoChatTheme
import com.example.vivochat.presentation.viewModel.darkmode_viewmodel.DarkModeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {
    private val darkModeViewModel: DarkModeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val isDark = darkModeViewModel.isDarkMode.collectAsState().value

            VivoChatTheme(userPreferenceDarkTheme = isDark) {

                val navController = rememberNavController()
                val viewModelStoreOwner = this

                val firestore = FirebaseFirestore.getInstance()
                val firebaseAuth = FirebaseAuth.getInstance()
                val fireBaseDataSource: RemoteDataSource = FirebaseRemoteDataSource()
                val userRepo: IUserRepository = UserRepository(fireBaseDataSource)

                NavHost(navController, "login") {

                    composable("splash") {
                        SplashScreen(navController, viewModelStoreOwner, firebaseAuth)
                    }

                    composable("login") {
                        Login(viewModelStoreOwner, navController)
                    }

                    composable("signup") {
                        SignupScreen(viewModelStoreOwner, navController, userRepo, firebaseAuth)
                    }

                    composable("navScreen") {
                        NavScreen(
                            navController,
                            viewModelStoreOwner,
                            userRepo,
                            firebaseAuth,
                            darkModeViewModel = darkModeViewModel
                        )
                    }

                    composable(
                        "chat/{userName}/{userId}",
                        arguments = listOf(
                            navArgument("userName") { type = NavType.StringType },
                            navArgument("userId") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val userId = backStackEntry.arguments?.getString("userId")!!
                        val userName = backStackEntry.arguments?.getString("userName")!!
                        ChatScreen(navController, userName, userId)
                    }

                }
            }
        }
    }

}

