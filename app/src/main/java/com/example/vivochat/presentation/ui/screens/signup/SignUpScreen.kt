package com.example.vivochat.presentation.ui.screens.signup

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.vivochat.presentation.ui.screens.profile_image.ProfileImageRoute
import com.example.vivochat.presentation.ui.screens.signup.components.SignupScreenContent
import com.example.vivochat.presentation.ui.screens.signup.viewmodel.SignupState
import com.example.vivochat.presentation.ui.screens.signup.viewmodel.SignupViewModel
import kotlinx.serialization.Serializable

@Serializable
data object SignupRoute
@Composable
fun SignupScreen(
    navController: NavController,
    viewModel: SignupViewModel = hiltViewModel(),
) {
    val ctx = LocalContext.current
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var passVisible by remember { mutableStateOf(false) }
    var confirmVisible by remember { mutableStateOf(false) }


    val signupState = viewModel.signupState.collectAsState()


    LaunchedEffect(signupState.value) {
        if (signupState.value is SignupState.Error) {
            val message = (signupState.value as SignupState.Error).message
            Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show()
        } else if (signupState.value is SignupState.Success) {
            Toast.makeText(ctx, "Account created successfully", Toast.LENGTH_SHORT).show()
            navController.navigate(ProfileImageRoute)
        }
    }

    SignupScreenContent(
        fullName = fullName,
        onFullNameChange = { fullName = it },

        email = email,
        onEmailChange = { email = it },

        phone = phone,
        onPhoneChange = { phone = it },

        password = password,
        onPasswordChange = { password = it },

        confirmPassword = confirmPassword,
        onConfirmPasswordChange = { confirmPassword = it },

        passVisible = passVisible,
        onPassVisibleChange = { passVisible = it },

        confirmVisible = confirmVisible,
        onConfirmVisibleChange = { confirmVisible = it },

        state = signupState.value,

        onSignupClick = {
            if (fullName.isEmpty()) {
                Toast.makeText(ctx, "First name required!", Toast.LENGTH_SHORT).show()
            } else if (email.isEmpty()) {
                Toast.makeText(ctx, "Email is required!", Toast.LENGTH_SHORT).show()
            } else if (password.length < 6) {
                Toast.makeText(ctx, "Password must be more than 6 characters!", Toast.LENGTH_SHORT)
                    .show()
            } else if (password != confirmPassword) {
                Toast.makeText(ctx, "Passwords don't match!", Toast.LENGTH_SHORT).show()
            } else if (phone.isEmpty()) {
                Toast.makeText(ctx, "Phone Number is required!", Toast.LENGTH_SHORT).show()
            } else if (!isValidEgyptianPhone(phone)) {
                Toast.makeText(ctx, "Invalid Egyptian phone number", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.signUp(fullName, email, password, phone)
            }

        }
    )
}

fun isValidEgyptianPhone(phone: String): Boolean {
    val regex = Regex("^(010|011|012|015)\\d{8}$")
    return regex.matches(phone)
}
