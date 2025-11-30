package com.example.vivochat.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vivochat.presentation.ui.screens.Nav.NavScreen
import com.example.vivochat.presentation.ui.screens.Story.StoryScreen
import com.example.vivochat.presentation.ui.screens.chat.ChatScreen
import com.example.vivochat.presentation.ui.screens.login.Login

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            val navController = rememberNavController()
//            NavHost(navController,"navScreen"){
//                composable("navscreen") { NavScreen() }
//                composable("chat") { ChatScreen() }
           // SignUpScreen() { }
            Login {  }
            }

        }
    }


