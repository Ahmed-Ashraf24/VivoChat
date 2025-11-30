package com.example.vivochat.presentation.ui.screens.Splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.vivochat.R

@Composable
fun SplashScreen(navController: NavController) {
    val animation =
        rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splashscreenanimation))


    val progress = animateLottieCompositionAsState(
        composition = animation.value,

        )
    LaunchedEffect(progress.progress) {
        if (progress.progress >= 1f) {
            navController.navigate("login") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }



    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LottieAnimation(
            composition = animation.value
        )
    }

}