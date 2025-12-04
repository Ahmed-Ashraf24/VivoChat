package com.example.vivochat.presentation.ui.screens.setting

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.vivochat.R
import com.example.vivochat.domain.entity.User
import com.example.vivochat.presentation.ui.screens.setting.components.ProfileSection
import com.example.vivochat.presentation.ui.screens.setting.components.SettingsItem
import com.example.vivochat.presentation.ui.theme.kumbuhFont
import com.example.vivochat.presentation.ui.theme.montserratFont
import com.example.vivochat.presentation.viewModel.setting_viewmodel.SettingsViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable



fun SettingsScreen(
    navController: NavController,
    settingViewModel: SettingsViewModel=hiltViewModel(),
    loggedUser: User
) {

    val darkMode by settingViewModel.isDarkMode.collectAsState()
    var selectedLanguage by remember { mutableStateOf("English") }
    var showLanguageSheet by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {



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
                    settingViewModel.toggleDarkMode()
                }
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        SettingsItem(
            title = "Log out",
            subtitle = "",
            icon = R.drawable.logout,
            onClick = {
                settingViewModel.signOut()
                navController.navigate("login")

            }
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
