package com.example.vivochat.presentation.ui.screens.login.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vivochat.authentication.AuthState
import com.example.vivochat.authentication.AuthViewModel
import com.example.vivochat.presentation.ui.theme.Primary

@Composable
fun LoginForm(modifier: Modifier = Modifier,
              authViewModel: AuthViewModel = viewModel()) {
    val authState = authViewModel.authState.collectAsState().value
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    EmailTextField(email.value, {
        email.value = it
    })
    Spacer(Modifier.height(20.dp))
    PasswordTextField(password.value, {
        password.value = it
    })
    Spacer(Modifier.height(4.dp))
    TextButton({}, modifier =modifier) {
        Text("Forgot Password?", color = Primary)
    }

    LoginButton({
        authViewModel.login(email.value, password.value)
    })
    when (authState) {
        AuthState.Loading -> {
            Spacer(Modifier.height(16.dp))
            CircularProgressIndicator()
        }
        is AuthState.Error -> {
            Spacer(Modifier.height(16.dp))

            Text(
                text = "Error: ${authState.message}",
                color = Color.Red
            )
        }
        is AuthState.Success -> {

            Spacer(Modifier.height(16.dp))
            Text("Login Successful! ")
        }
        else -> Unit // Idle
    }
}