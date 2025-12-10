package com.example.vivochat.presentation.ui.screens.splash

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.vivochat.R
import com.example.vivochat.presentation.ui.screens.splash.viewmodel.SplashState
import com.example.vivochat.presentation.ui.screens.splash.viewmodel.SplashViewModel
import kotlinx.coroutines.delay
import android.Manifest
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.vivochat.presentation.ui.screens.login.LoginRoute
import com.example.vivochat.presentation.ui.screens.main.MainRoute
import kotlinx.serialization.Serializable

@Serializable
data object SplashRoute

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel : SplashViewModel= hiltViewModel()
) {
  val state = viewModel.autoLogin.collectAsState()

    val animation =
        rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splashscreenanimation))


    val progress = animateLottieCompositionAsState(
        composition = animation.value,
        iterations = Int.MAX_VALUE
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.autoLogin()
        }

    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.READ_CONTACTS)
    }


    LaunchedEffect(state.value) {
        try{
            if (state.value is SplashState.AutoLoginFailed) {
                delay(1000)

                navController.navigate(LoginRoute)
            } else if (state.value is SplashState.AutoLoginSuccess) {
                delay(1000)

                navController.navigate(MainRoute){
                    popUpTo (0){ inclusive=true }
                }

            }
        }catch (e : Exception){
            Log.d("Ausifaaaa",e.toString())
        }
    }



    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LottieAnimation(
            composition = animation.value,
            progress = progress.value
        )
    }

}