package com.example.vivochat.presentation.ui.screens.reel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.vivochat.R
import kotlinx.serialization.Serializable

@Serializable
data object ReelRoute

fun NavGraphBuilder.reelScreen(
    navigateBack: () -> Unit
) {
    composable<ReelRoute> {
        ReelScreen(navigateBack = navigateBack)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReelScreen(
    navigateBack: () -> Unit
) {
    Scaffold(
        contentWindowInsets = WindowInsets(),
        containerColor = Color.Yellow,
        topBar = {
            TopAppBar(
                title = {
                    Text("ReelScreen")
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            painter = painterResource(R.drawable.outline_arrow_back_ios_24),
                            tint = Color.Unspecified,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            Text("Reel Screen")
        }
    }
}