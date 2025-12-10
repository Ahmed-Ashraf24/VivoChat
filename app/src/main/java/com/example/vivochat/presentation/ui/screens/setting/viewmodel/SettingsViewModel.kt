package com.example.vivochat.presentation.ui.screens.setting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vivochat.domain.entity.User
import com.example.vivochat.domain.repository.IAuthRepo
import com.example.vivochat.domain.repository.IUserRepository
import com.example.vivochat.presentation.utility.ThemeManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authRpo: IAuthRepo,
    private val userRepo: IUserRepository,
    private val themeManager: ThemeManager
) : ViewModel() {
    private val _userData = MutableStateFlow<User>(
        User(
            userId = "",
            fullName = "",
            email = "",
            phoneNum = "",
            imageUrl = "",
            stories = emptyList()
        )
    )
    val userData= _userData.asStateFlow()
    init {
        getCurrentUserData()
    }
    private fun getCurrentUserData(){

        viewModelScope.launch {
            val res = userRepo.getUserData(
                authRpo.getLoggedUserIdOrNull()!!
            )
            if(res.isSuccess){
                _userData.value = res.getOrNull()!!
            }
        }
    }
    val isDarkMode = themeManager.isDarkMode

    fun toggleDarkMode() = themeManager.toggle()
    fun signOut(){
        viewModelScope.launch {
            authRpo.signOutUser()
        }
    }
}