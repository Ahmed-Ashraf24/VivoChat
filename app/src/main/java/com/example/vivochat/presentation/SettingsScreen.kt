package com.example.vivochat.presentation
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vivochat.R
import com.example.vivochat.presentation.ui.theme.kumbuhFont
import com.example.vivochat.presentation.ui.theme.montserratFont

@Composable
fun SettingsScreen() {

    var darkMode by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Settings",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = montserratFont
        )

        Spacer(modifier = Modifier.height(24.dp))

        // ******   Profile and email    ******
        Row(verticalAlignment = Alignment.CenterVertically) {

            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "profile",
                modifier = Modifier
                    .size(75.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text("Shreya", fontSize = 20.sp,color=Color.Black, fontWeight = FontWeight.ExtraBold, fontFamily = kumbuhFont)
                Text("xyz123@gmail.com", fontSize = 15.sp, color = Color.Gray, fontFamily = kumbuhFont , fontWeight = FontWeight.ExtraBold)
            }
        }

        Spacer(modifier = Modifier.height(26.dp))

        // *************** Items ***************

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

        // ****** Dark Mode Row ******
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text("Dark Mode", fontSize = 18.sp , fontFamily = kumbuhFont , fontWeight = FontWeight.Bold)
            }

            Switch(checked = darkMode, onCheckedChange = { darkMode = it })
        }

        Spacer(modifier = Modifier.height(14.dp))

        SettingsItem(
            title = "Log out",
            subtitle = "",
            icon = R.drawable.logout
        )
    }
}


@Composable
fun SettingsItem(title: String, subtitle: String, icon: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(title, fontSize = 18.sp, fontFamily = kumbuhFont, fontWeight = FontWeight.Bold)
            if (subtitle.isNotEmpty()) {
                Text(subtitle, fontSize = 13.sp, color = Color.Gray)
            }
        }

        Image(
            painter = painterResource(id = icon),
            contentDescription = "icon",
            modifier = Modifier.size(22.dp)
        )
    }
}
@Composable
@Preview
fun prevS(){
    SettingsScreen()
}