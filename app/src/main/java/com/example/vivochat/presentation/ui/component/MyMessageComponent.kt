package com.example.vivochat.presentation.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vivochat.presentation.ui.theme.Poppins
import com.example.vivochat.presentation.ui.theme.Primary

@Composable
fun MyMessageComponent(modifier: Modifier = Modifier,message:String) {
    var isVisible by remember { mutableStateOf(false) }
    Column (modifier = modifier,horizontalAlignment = Alignment.End
       ) {

    Surface(modifier=Modifier.clickable{
        isVisible=!isVisible
    },shape = RoundedCornerShape(topStart = 15.dp, bottomStart = 15.dp, topEnd = 15.dp), color = Primary) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Text(message, fontFamily = Poppins, color = Color.White, modifier = Modifier.padding(10.dp))
        }
    }
        AnimatedVisibility(visible = isVisible) {
            Text("4:00pm", fontFamily = Poppins, color = Color.Gray, modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }

}