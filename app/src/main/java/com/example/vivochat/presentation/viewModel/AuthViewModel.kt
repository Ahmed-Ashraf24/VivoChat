package com.example.vivochat.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.example.vivochat.domain.entity.AuthState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun showError(message: String) {
        _authState.value = AuthState.Error(message)

    }

    fun signUp(email: String, password: String) {
        _authState.value = AuthState.Loading

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid ?: ""
                    _authState.value = AuthState.Success(uid)
                } else {
                    _authState.value = AuthState.Error(
                        task.exception?.localizedMessage ?: "Unknown Error"
                    )
                }
            }
    }

    fun login(email: String, password: String) {
        _authState.value = AuthState.Loading

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid ?: ""
                    _authState.value = AuthState.Success(uid)
                } else {
                    _authState.value = AuthState.Error(
                        task.exception?.localizedMessage ?: "Login failed"
                    )
                }
            }
    }
}