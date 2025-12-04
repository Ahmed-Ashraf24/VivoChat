package com.example.vivochat.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import com.example.vivochat.domain.repository.IUserRepository
import com.example.vivochat.presentation.ui.screens.signup.components.SignupScreenContent
import com.example.vivochat.presentation.ui.theme.Primary
import com.example.vivochat.presentation.ui.theme.interFont
import com.example.vivochat.presentation.ui.theme.sansFont
import com.example.vivochat.presentation.viewModel.signup_view_model.SignupState
import com.example.vivochat.presentation.viewModel.signup_view_model.SignupViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SignupScreen(

    navController: NavController,
    viewModel: SignupViewModel= hiltViewModel()
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
        if(signupState.value is SignupState.Error){
            val message = (signupState.value as SignupState.Error).message
            Toast.makeText(ctx,message,Toast.LENGTH_SHORT).show()
        }else if(signupState.value is SignupState.Success){
            Toast.makeText(ctx,"Account created successfully",Toast.LENGTH_SHORT).show()
            navController.navigate("profileImageScreen")
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
                Toast.makeText(ctx, "Password must be more than 6 characters!", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(ctx, "Passwords don't match!", Toast.LENGTH_SHORT).show()
            } else if (phone.isEmpty()) {
                Toast.makeText(ctx, "Phone Number is required!", Toast.LENGTH_SHORT).show()
            }else if(!isValidEgyptianPhone(phone)){
                Toast.makeText(ctx, "Invalid Egyptian phone number", Toast.LENGTH_SHORT).show()
            }

            else {
                viewModel.signUp(fullName, email, password, phone)
            }

        }
    )
}
fun isValidEgyptianPhone(phone: String): Boolean {
    val regex = Regex("^(010|011|012|015)\\d{8}\$")
    return regex.matches(phone)
}
