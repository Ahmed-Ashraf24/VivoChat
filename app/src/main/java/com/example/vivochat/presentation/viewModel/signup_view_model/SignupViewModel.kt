package com.example.vivochat.presentation.viewModel.signup_view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vivochat.domain.repository.IUserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignupViewModel(
    private val userRepository: IUserRepository,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {


    private val _signupState = MutableStateFlow<SignupState>(SignupState.Idle)
    val signupState: StateFlow<SignupState>
        get() = _signupState


    fun signUp(fullName: String, email: String, password: String, phoneNum: String) {

        _signupState.value = SignupState.Loading

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = firebaseAuth.currentUser?.uid ?: ""
                    uploadUserData(uid, fullName, email, phoneNum)
                } else {
                    _signupState.value = SignupState.Error("Authentication failed")
                }
            }

    }

    fun uploadUserData(userId: String, fullName: String, email: String, phoneNum: String) {
        viewModelScope.launch {

            val res = userRepository.uploadUserData(userId, fullName, email, phoneNum)
            if (res.isSuccess) {
                _signupState.value = SignupState.Success(userId)
            } else {
                _signupState.value = SignupState.Error("Failed to upload user data")
            }

        }
    }
}