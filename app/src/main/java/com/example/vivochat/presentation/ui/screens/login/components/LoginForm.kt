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
import com.example.vivochat.domain.entity.AuthState
import com.example.vivochat.presentation.viewModel.AuthViewModel
import com.example.vivochat.presentation.ui.theme.Primary

@Composable
fun LoginForm(modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = viewModel()
    ,onLoginClicked:(String,String)->Unit) {
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
        onLoginClicked(email.value,password.value)
    })

}