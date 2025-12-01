package com.example.vivochat.presentation.viewModel.login_view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vivochat.data.dataSource.local.LocalDataSource
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel (
    private val localDataSource: LocalDataSource
): ViewModel(){
    private val auth = FirebaseAuth.getInstance()

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState



    fun login(email: String, password: String) {
        _loginState.value = LoginState.Loading

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid ?: ""
                    _loginState.value = LoginState.Success(uid)
                    viewModelScope.launch {
                        localDataSource.saveData(uid)
                    }
                } else {
                    _loginState.value = LoginState.Error(
                        task.exception?.localizedMessage ?: "Login failed"
                    )
                }
            }
    }


    fun clearState(){
        _loginState.value = LoginState.Idle
    }
}