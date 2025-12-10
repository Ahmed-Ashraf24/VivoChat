package com.example.vivochat.presentation.ui.screens.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vivochat.domain.repository.IAuthRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class LoginViewModel @Inject constructor(
private val authRepo: IAuthRepo
): ViewModel(){

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState



    fun login(email: String, password: String) {
        _loginState.value = LoginState.Loading
        viewModelScope.launch {
            authRepo.loginUser(email,password).fold(
            onSuccess = {userId->
                _loginState.value = LoginState.Success(userId)

            },
            onFailure = {error->
                error.printStackTrace()
                _loginState.value = LoginState.Error(
                    error.message ?: "Login failed"
                )
            }
        )
        }
    }


    fun clearState(){
        _loginState.value = LoginState.Idle
    }
}