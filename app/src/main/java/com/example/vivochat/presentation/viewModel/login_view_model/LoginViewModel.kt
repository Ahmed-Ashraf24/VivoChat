package com.example.vivochat.presentation.viewModel.login_view_model

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel : ViewModel(){
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