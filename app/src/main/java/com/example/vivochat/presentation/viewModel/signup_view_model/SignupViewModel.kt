package com.example.vivochat.presentation.viewModel.signup_view_model

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SignupViewModel : ViewModel(){
    private val auth = FirebaseAuth.getInstance()

    private val _signupState = MutableStateFlow<SignupState>(SignupState.Idle)
    val signupState : StateFlow<SignupState>
        get() = _signupState



    fun signUp(email: String, password: String){
        _signupState.value = SignupState.Loading

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid ?: ""
                    _signupState.value = SignupState.Success(uid)
                } else {
                    _signupState.value = SignupState.Error("Unknown Error")
                }
            }
    }
}