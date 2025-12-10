package com.example.vivochat.presentation.ui.screens.signup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vivochat.domain.repository.IAuthRepo
import com.example.vivochat.domain.repository.IUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SignupViewModel @Inject constructor(
    private val userRepository: IUserRepository,
    private val authRepo: IAuthRepo
) : ViewModel() {


    private val _signupState = MutableStateFlow<SignupState>(SignupState.Idle)
    val signupState: StateFlow<SignupState>
        get() = _signupState


    fun signUp(fullName: String, email: String, password: String, phoneNum: String) {

        _signupState.value = SignupState.Loading
        viewModelScope.launch {
            authRepo.signUpUser(email,password).fold(
            onSuccess = {userId->
                uploadUserData(userId, fullName, email, phoneNum)

            },
            onFailure = {errorMessage->
                _signupState.value = SignupState.Error(errorMessage.message?:"signup failed ")

            }
        )
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