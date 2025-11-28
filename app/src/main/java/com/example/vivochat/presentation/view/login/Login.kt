package com.example.vivochat.presentation.view.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vivochat.presentation.ui.theme.Primary
import com.example.vivochat.presentation.view.login.components.CreateAccRow
import com.example.vivochat.presentation.view.login.components.EmailTextField
import com.example.vivochat.presentation.view.login.components.LoginButton
import com.example.vivochat.presentation.view.login.components.LoginForm
import com.example.vivochat.presentation.view.login.components.LoginHeader
import com.example.vivochat.presentation.view.login.components.OrDivider
import com.example.vivochat.presentation.view.login.components.PasswordTextField

@Composable
fun Login() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .imePadding()
            .background(color = Color.White)
            .padding(top = 198.dp, start = 16.dp, end = 16.dp, bottom = 12.dp)
    ) {
        LoginHeader(Modifier.align(Alignment.CenterHorizontally))
        Spacer(Modifier.height(30.dp))
        LoginForm(Modifier.align(Alignment.End))
        Spacer(Modifier.height(30.dp))
        OrDivider()
        Spacer(Modifier.height(30.dp))
        CreateAccRow(Modifier.align(Alignment.CenterHorizontally), {
        })
    }
}