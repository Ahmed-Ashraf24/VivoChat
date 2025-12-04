package com.example.vivochat.presentation.ui.screens.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import com.example.vivochat.R
import com.example.vivochat.data.dataSource.firebase_remote_datasource.firebase_utility.FirebaseInstance.firebaseAuth
import com.example.vivochat.domain.entity.User
import com.example.vivochat.domain.repository.IMediaRepository
import com.example.vivochat.domain.repository.IUserRepository
import com.example.vivochat.presentation.ui.screens.setting.components.ProfileSection
import com.example.vivochat.presentation.ui.screens.setting.components.SettingsItem
import com.example.vivochat.presentation.ui.theme.Primary
import com.example.vivochat.presentation.ui.theme.kumbuhFont
import com.example.vivochat.presentation.ui.theme.montserratFont
import com.example.vivochat.presentation.viewModel.darkmode_viewmodel.DarkModeViewModel
import kotlin.math.log


@OptIn(ExperimentalMaterial3Api::class)
@Composable



fun SettingsScreen(
    navController: NavController,
    darkModeViewModel: DarkModeViewModel,
    loggedUser: User
) {

    val darkMode by darkModeViewModel.isDarkMode.collectAsState()
    var selectedLanguage by remember { mutableStateOf("English") }
    var showLanguageSheet by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Settings",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = montserratFont,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(24.dp))

        ProfileSection(userName = loggedUser.fullName, userImageUrl = loggedUser.imageUrl, userEmail = loggedUser.email)

        Spacer(modifier = Modifier.height(26.dp))

        SettingsItem(
            title = "Account",
            subtitle = "Security notifications, change number",
            icon = R.drawable.profile,
            {}
        )

        SettingsItem(
            title = "App language",
            subtitle = "$selectedLanguage",
            icon = R.drawable.language,
            onClick = { showLanguageSheet = true }
        )

        SettingsItem(
            title = "Help",
            subtitle = "Help centre, contact us, privacy policy",
            icon = R.drawable.helpp,
            {}
        )

        // Dark mode switch
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    "Dark Mode",
                    fontSize = 18.sp,
                    fontFamily = kumbuhFont,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground

                )
            }

            Switch(
                checked = darkMode,
                onCheckedChange = {isChecked ->
                    darkModeViewModel.toggleDarkMode(isChecked)
                }
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        SettingsItem(
            title = "Log out",
            subtitle = "",
            icon = R.drawable.logout,
        )


        if (showLanguageSheet) {
            ModalBottomSheet(
                onDismissRequest = { showLanguageSheet = false }
            ) {

                val languages = listOf(
                    "English", "Arabic", "French", "German", "Spanish", "Italian", "Turkish"
                )

                Column(modifier = Modifier.padding(20.dp)) {

                    Text(
                        text = "Choose App Language",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = montserratFont
                    )

                    Spacer(Modifier.height(16.dp))

                    languages.forEach { lang ->
                        TextButton(
                            onClick = {
                                selectedLanguage = lang
                                showLanguageSheet = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = lang,
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }

    }
}
