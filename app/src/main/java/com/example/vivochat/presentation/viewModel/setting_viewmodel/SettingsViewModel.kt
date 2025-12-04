package com.example.vivochat.presentation.viewModel.setting_viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vivochat.domain.repository.IUserRepository
import com.example.vivochat.presentation.utility.ThemeManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userRepo: IUserRepository,
    private val themeManager: ThemeManager
) : ViewModel() {

    val isDarkMode = themeManager.isDarkMode

    fun toggleDarkMode() = themeManager.toggle()
    fun signOut(){
        viewModelScope.launch {
            userRepo.signOutUser()
        }
    }
}
