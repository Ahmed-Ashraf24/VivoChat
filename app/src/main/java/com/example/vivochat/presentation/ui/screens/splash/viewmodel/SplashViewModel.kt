package com.example.vivochat.presentation.ui.screens.splash.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import android.util.Log
import com.example.vivochat.domain.repository.IAuthRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepo: IAuthRepo
) : ViewModel() {


    private val _autoLogin = MutableStateFlow<SplashState>(SplashState.Idle)
    val autoLogin: StateFlow<SplashState>
        get() = _autoLogin


    fun autoLogin() {
       try{
           if (authRepo.getLoggedUserIdOrNull() != null) {
               _autoLogin.value = SplashState.AutoLoginSuccess
           } else {
               _autoLogin.value = SplashState.AutoLoginFailed
           }
       }catch (e : Exception){
           Log.d("Errro r ya mora",e.toString())
       }
    }



}