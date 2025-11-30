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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import com.example.vivochat.presentation.ui.theme.Primary
import com.example.vivochat.presentation.ui.theme.interFont
import com.example.vivochat.presentation.ui.theme.sansFont
import com.example.vivochat.presentation.viewModel.login_view_model.LoginState
import com.example.vivochat.presentation.viewModel.signup_view_model.SignupState
import com.example.vivochat.presentation.viewModel.signup_view_model.SignupViewModel

@Composable
fun SignupScreen(
    viewModelStoreOwner: ViewModelStoreOwner,
    navController: NavController
) {
    val ctx = LocalContext.current
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var passVisible by remember { mutableStateOf(false) }
    var confirmVisible by remember { mutableStateOf(false) }


    val viewModel = ViewModelProvider(viewModelStoreOwner).get(SignupViewModel::class.java)
    val signupState = viewModel.signupState.collectAsState()


    LaunchedEffect(signupState.value) {
        if(signupState.value is SignupState.Error){
            val message = (signupState.value as SignupState.Error).message
            Toast.makeText(ctx,message,Toast.LENGTH_SHORT).show()
        }else if(signupState.value is SignupState.Success){
            navController.navigate("splash")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.Start
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        // ******  Sign up ******
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

        // ****** Full Name ******
        Text("Full Name", fontSize = 12.sp ,color=Color.Gray, fontFamily = sansFont)
        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(18.dp))

        // ****** Email ******
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

        // ****** Phone Number ******
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

        // ****** Password ******
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

        // ****** Confirm Password ******
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

        // ****** Signup ******

        if(signupState.value is SignupState.Loading){
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator(color = Primary)
            }
        }else{
            Button(
                onClick = {
                    if(fullName.isEmpty()){
                        Toast.makeText(ctx,"first name required",Toast.LENGTH_SHORT).show()
                    }else if(email.isEmpty()){
                        Toast.makeText(ctx,"email is required",Toast.LENGTH_SHORT).show()
                    }else if(password.length<6){
                        Toast.makeText(ctx,"Password must be more than 6 chars",Toast.LENGTH_SHORT).show()
                    }else if(password!=confirmPassword){
                        Toast.makeText(ctx,"passwords don't match",Toast.LENGTH_SHORT).show()
                    }else if(phone.isEmpty()){
                        Toast.makeText(ctx,"phone is required",Toast.LENGTH_SHORT).show()
                    }else{
                        viewModel.signUp(email,password)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary
                )
            ) {
                Text(text = "Signup", fontSize = 14.sp, color = Color.White , fontFamily = interFont)
            }
        }
    }
}