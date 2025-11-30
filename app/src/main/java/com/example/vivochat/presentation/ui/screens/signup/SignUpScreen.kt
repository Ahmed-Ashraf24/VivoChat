package com.example.vivochat.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vivochat.authentication.AuthState
import com.example.vivochat.authentication.AuthViewModel
import com.example.vivochat.presentation.ui.theme.interFont
import com.example.vivochat.presentation.ui.theme.sansFont

@Composable

fun SignUpScreen(
    viewModel: AuthViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onSignUpSuccess: () -> Unit
) {

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var passVisible by remember { mutableStateOf(false) }
    var confirmVisible by remember { mutableStateOf(false) }

    val state by viewModel.authState.collectAsState()

    LaunchedEffect(state) {
        when (state) {
            is AuthState.Success -> {
                onSignUpSuccess()  // ← هنا مش هيدي error
            }
            is AuthState.Error -> {
                println("Error: ${(state as AuthState.Error).message}")
            }
            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.Start
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Sign up",
            fontSize = 32.sp,
            fontWeight = Bold,
            fontFamily = interFont
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Create an account to continue!",
            fontSize = 12.sp,
            color = Color.Gray,
            fontFamily = interFont
        )

        Spacer(modifier = Modifier.height(26.dp))

        Text("Full Name", fontSize = 12.sp ,color=Color.Gray, fontFamily = sansFont)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text("Email", fontSize = 12.sp,color=Color.Gray)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text("Phone Number", fontSize = 12.sp,color=Color.Gray)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text("Password", fontSize = 12.sp,color=Color.Gray)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (passVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passVisible = !passVisible }) {
                    Icon(
                        imageVector = if (passVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "toggle pass"
                    )
                }
            },
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text("Confirm Password", fontSize = 12.sp,color=Color.Gray)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (confirmVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { confirmVisible = !confirmVisible }) {
                    Icon(
                        imageVector = if (confirmVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "toggle pass"
                    )
                }
            },
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.height(30.dp))

        // ---------------- SIGNUP BUTTON ----------------
        Button(
            onClick = {
                if (password != confirmPassword) {
                    viewModel.showError("Passwords do not match")
                } else if (email.isBlank() || password.isBlank()) {
                    viewModel.showError("Please fill all fields")
                } else {
                    viewModel.signUp(email, password)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0A4D9F)
            )
        ) {
            Text(text = "Signup", fontSize = 14.sp, color = Color.White , fontFamily = interFont)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // state handling xxx
        when (state) {

            is AuthState.Loading -> {
                CircularProgressIndicator()
            }

            is AuthState.Error -> {
                Text(
                    text = (state as AuthState.Error).message,
                    color = Color.Red,
                    fontSize = 14.sp
                )
            }

            is AuthState.Success -> {
                Text(
                    text = "Account created successfully!",
                    color = Color(0xFF0A4D9F),
                    fontWeight = Bold
                )

                onSignUpSuccess()
            }

            else -> {}
        }
    }
}
