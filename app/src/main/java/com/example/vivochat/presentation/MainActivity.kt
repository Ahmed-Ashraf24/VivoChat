package com.example.vivochat.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vivochat.data.dataSource.local.LocalDataSource
import com.example.vivochat.data.repository.UserRepository
import com.example.vivochat.domain.repository.IUserRepository
import com.example.vivochat.presentation.ui.screens.nav.NavScreen
import com.example.vivochat.presentation.ui.screens.Splash.SplashScreen

import com.example.vivochat.presentation.ui.screens.chat.ChatScreen
import com.example.vivochat.presentation.ui.screens.login.Login
import com.example.vivochat.presentation.view.home.Home
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val viewModelStoreOwner = this
            val navController = rememberNavController()
            val localDataSource = LocalDataSource(this)
            val userRepo: IUserRepository = UserRepository(FirebaseFirestore.getInstance())
            NavHost(navController, "splash") {
                composable("splash") { SplashScreen(navController,viewModelStoreOwner,localDataSource) }
                composable("login") { Login(viewModelStoreOwner, navController, localDataSource) }
                composable("signup") { SignupScreen(viewModelStoreOwner, navController, userRepo,localDataSource) }
                composable("navScreen") {
                    NavScreen(
                        navController,
                        viewModelStoreOwner,
                        userRepo,
                        localDataSource
                    )
                }
                composable("chat") { ChatScreen(navController) }
            }


        }
    }
}

