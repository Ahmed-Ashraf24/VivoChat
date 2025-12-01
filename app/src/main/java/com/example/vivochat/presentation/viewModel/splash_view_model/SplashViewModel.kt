package com.example.vivochat.presentation.viewModel.splash_view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vivochat.data.dataSource.local.LocalDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val localDataSource: LocalDataSource
) : ViewModel() {


    private val _autoLogin = MutableStateFlow<SplashState>(SplashState.Idle)
    val autoLogin: StateFlow<SplashState>
        get() = _autoLogin


    fun autoLogin() {
      try{
          viewModelScope.launch {
              if (localDataSource.getUserId() == null) {
                  _autoLogin.value = SplashState.AutoLoginFailed
              } else {
                  _autoLogin.value = SplashState.AutoLoginSuccess
              }
          }
      }catch (e : Exception){
          Log.d("Ecefjpdos",e.toString())
      }
    }

    init {
        autoLogin()
    }
}