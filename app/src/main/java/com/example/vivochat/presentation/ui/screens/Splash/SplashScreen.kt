package com.example.vivochat.presentation.ui.screens.Splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.vivochat.R
import com.example.vivochat.presentation.viewModel.splash_view_model.SplashState
import com.example.vivochat.presentation.viewModel.splash_view_model.SplashViewModel
import com.example.vivochat.presentation.viewModel.splash_view_model.SplashViewModelFac
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    viewModelStoreOwner: ViewModelStoreOwner,
    firebaseAuth: FirebaseAuth
) {
    val viewModelFac = SplashViewModelFac(firebaseAuth)
    val viewModel =
        ViewModelProvider(viewModelStoreOwner, viewModelFac).get(SplashViewModel::class.java)
    val state = viewModel.autoLogin.collectAsState()

    val animation =
        rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splashscreenanimation))


    val progress = animateLottieCompositionAsState(
        composition = animation.value,
        iterations = Int.MAX_VALUE
        )
    LaunchedEffect(state.value) {
        if (state.value is SplashState.AutoLoginFailed) {
            delay(1000)
            navController.navigate("login")
        } else if (state.value is SplashState.AutoLoginSuccess) {
            delay(1000)
            navController.navigate("navScreen")
        }
    }



    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LottieAnimation(
            composition = animation.value,
            progress = progress.value
        )
    }

}