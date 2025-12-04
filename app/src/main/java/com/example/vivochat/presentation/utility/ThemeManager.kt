package com.example.vivochat.presentation.utility

import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeManager @Inject constructor() {

    private val _isDarkMode = MutableStateFlow(false)  // default = light
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    fun setDarkMode(isDark: Boolean) {
        _isDarkMode.value = isDark
    }

    fun toggle() {
        _isDarkMode.value = !_isDarkMode.value
    }
}