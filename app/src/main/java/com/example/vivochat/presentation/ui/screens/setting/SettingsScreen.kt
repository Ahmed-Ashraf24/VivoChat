package com.example.vivochat.presentation.ui.screens.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vivochat.R
import com.example.vivochat.presentation.ui.screens.setting.components.ProfileSection
import com.example.vivochat.presentation.ui.screens.setting.components.SettingsItem
import com.example.vivochat.presentation.ui.theme.kumbuhFont
import com.example.vivochat.presentation.ui.theme.montserratFont
import com.example.vivochat.presentation.viewModel.darkmode_viewmodel.DarkModeViewModel

@Composable
fun SettingsScreen(
    darkModeViewModel: DarkModeViewModel   // 👈 بيجيلك من NavScreen
) {

    val darkMode by darkModeViewModel.isDarkMode.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {

        Text(
            text = "Settings",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = montserratFont,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(24.dp))

        ProfileSection()

        Spacer(modifier = Modifier.height(26.dp))

        SettingsItem(
            title = "Account",
            subtitle = "Security notifications, change number",
            icon = R.drawable.profile
        )

        SettingsItem(
            title = "App language",
            subtitle = "English (device's language)",
            icon = R.drawable.language
        )

        SettingsItem(
            title = "Help",
            subtitle = "Help centre, contact us, privacy policy",
            icon = R.drawable.helpp
        )

        // Dark mode switch
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
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
                    darkModeViewModel.toggleDarkMode(isChecked)   // 👈 يغيّر القيمة
                }
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        SettingsItem(
            title = "Log out",
            subtitle = "",
            icon = R.drawable.logout
        )
    }
}
