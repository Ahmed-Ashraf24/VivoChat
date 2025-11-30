package com.example.vivochat.presentation.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vivochat.presentation.ui.screens.login.components.CreateAccRow
import com.example.vivochat.presentation.ui.screens.login.components.LoginForm
import com.example.vivochat.presentation.ui.screens.login.components.LoginHeader
import com.example.vivochat.presentation.ui.screens.login.components.OrDivider
@Composable
fun Login(navControler: NavController) {
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
        LoginForm(Modifier.align(Alignment.End), onLoginClicked = {navControler.navigate("navscreen")})
        Spacer(Modifier.height(30.dp))
        OrDivider()
        Spacer(Modifier.height(30.dp))
        CreateAccRow(Modifier.align(Alignment.CenterHorizontally), {
            navControler.navigate("signup")
        })
    }
}