package com.example.vivochat.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vivochat.data.dataSource.MediaDatasource.CloudinaryDataSource
import com.example.vivochat.data.dataSource.firebase_remote_datasource.FirebaseRemoteDataSource
import com.example.vivochat.data.dataSource.RemoteDataSource
import com.example.vivochat.data.repository.CloudinaryRepository

import com.example.vivochat.data.repository.UserRepository
import com.example.vivochat.domain.entity.Contact
import com.example.vivochat.domain.repository.IUserRepository
import com.example.vivochat.presentation.ui.screens.Contacts.Contacts
import com.example.vivochat.presentation.ui.screens.Splash.SplashScreen
import com.example.vivochat.presentation.ui.screens.chat.ChatScreen
import com.example.vivochat.presentation.ui.screens.login.Login
import com.example.vivochat.presentation.ui.screens.nav.NavScreen
import com.example.vivochat.presentation.ui.screens.profile_image.ProfileImageScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLDecoder

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val viewModelStoreOwner = this
            val navController = rememberNavController()
            val firebaseAuth = FirebaseAuth.getInstance()
            val fireBaseDataSource: RemoteDataSource = FirebaseRemoteDataSource()
            val userRepo: IUserRepository = UserRepository(fireBaseDataSource)

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
                        navController
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
            }


        }
    }
}

