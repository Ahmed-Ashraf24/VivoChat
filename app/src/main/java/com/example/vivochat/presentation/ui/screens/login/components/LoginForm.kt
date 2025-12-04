package com.example.vivochat.presentation.ui.screens.login.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vivochat.presentation.ui.theme.Primary
import com.example.vivochat.presentation.viewModel.login_view_model.LoginState
import com.example.vivochat.presentation.viewModel.login_view_model.LoginViewModel
import kotlin.math.log

@Composable
fun LoginForm(modifier: Modifier = Modifier,viewModel: LoginViewModel,navController: NavController) {
    val ctx = LocalContext.current
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val loginState = viewModel.loginState.collectAsState()

    LaunchedEffect(loginState.value) {

        if(loginState.value is LoginState.Error){
            val message = (loginState.value as LoginState.Error).message
            Toast.makeText(ctx,message,Toast.LENGTH_SHORT).show()
        }else if(loginState.value is LoginState.Success){
            navController.navigate("navScreen")

        }
    }
    DisposableEffect(Unit) {
        onDispose {
            viewModel.clearState()
        }
    }


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

    if(loginState.value is LoginState.Loading){
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator(color = Primary)
        }
    }else{
        LoginButton({
            if(email.value.isEmpty()){
                Toast.makeText(ctx,"Email is empty",Toast.LENGTH_SHORT).show()
            }
            else if(password.value.length<6){
                Toast.makeText(ctx,"Password must be more than 6 chars",Toast.LENGTH_SHORT).show()
            }else{
                viewModel.login(email.value,password.value)
            }

        })
    }

}