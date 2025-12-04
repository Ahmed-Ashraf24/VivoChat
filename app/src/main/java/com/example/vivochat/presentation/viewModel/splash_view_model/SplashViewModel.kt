package com.example.vivochat.presentation.viewModel.splash_view_model

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import com.example.vivochat.domain.entity.Contact
import com.example.vivochat.domain.repository.IUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userRepo: IUserRepository
) : ViewModel() {


    private val _autoLogin = MutableStateFlow<SplashState>(SplashState.Idle)
    val autoLogin: StateFlow<SplashState>
        get() = _autoLogin


    fun autoLogin() {
       try{
           if (userRepo.getLoggedUserIdOrNull() != null) {
               _autoLogin.value = SplashState.AutoLoginSuccess
           } else {
               _autoLogin.value = SplashState.AutoLoginFailed
           }
       }catch (e : Exception){
           Log.d("Errro r ya mora",e.toString())
       }
    }



}