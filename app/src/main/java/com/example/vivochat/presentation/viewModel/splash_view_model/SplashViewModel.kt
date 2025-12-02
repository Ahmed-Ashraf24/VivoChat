package com.example.vivochat.presentation.viewModel.splash_view_model

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SplashViewModel(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {


    private val _autoLogin = MutableStateFlow<SplashState>(SplashState.Idle)
    val autoLogin: StateFlow<SplashState>
        get() = _autoLogin


    fun autoLogin() {
        if(firebaseAuth.currentUser!=null){
            //auto login the user
            _autoLogin.value = SplashState.AutoLoginSuccess
        }else{
            _autoLogin.value = SplashState.AutoLoginFailed
        }
    }

    init {
        autoLogin()
    }
}