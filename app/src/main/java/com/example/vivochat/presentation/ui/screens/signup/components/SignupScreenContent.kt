package com.example.vivochat.presentation.ui.screens.signup.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vivochat.presentation.ui.theme.interFont
import com.example.vivochat.presentation.viewModel.signup_view_model.SignupState

@Composable
fun SignupScreenContent(
    fullName: String,
    onFullNameChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    phone: String,
    onPhoneChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit,
    passVisible: Boolean,
    onPassVisibleChange: (Boolean) -> Unit,
    confirmVisible: Boolean,
    onConfirmVisibleChange: (Boolean) -> Unit,
    state: SignupState,
    onSignupClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
    ) {

        Spacer(Modifier.height(20.dp))
        Column(Modifier.fillMaxWidth().padding(top = 10.dp),horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Sign up", fontSize = 32.sp, fontWeight = Bold, fontFamily = interFont)
        Spacer(Modifier.height(6.dp))
        Text("Create an account to continue!", fontSize = 12.sp, color = Color.Gray)
        }
        Spacer(Modifier.height(26.dp))

        SignupInputField(label = "Full Name", value = fullName, onChange = onFullNameChange)
        Spacer(Modifier.height(18.dp))

        SignupEmailField(email, onEmailChange)
        Spacer(Modifier.height(18.dp))

        SignupInputField(label = "Phone Number", value = phone, onChange = onPhoneChange)
        Spacer(Modifier.height(18.dp))

        SignupPasswordField(
            label = "Password",
            value = password,
            visible = passVisible,
            onChange = onPasswordChange,
            onVisibleToggle = onPassVisibleChange
        )
        Spacer(Modifier.height(18.dp))

        SignupPasswordField(
            label = "Confirm Password",
            value = confirmPassword,
            visible = confirmVisible,
            onChange = onConfirmPasswordChange,
            onVisibleToggle = onConfirmVisibleChange
        )

        Spacer(Modifier.height(30.dp))

        SignupButton(state, onSignupClick)
    }
}
