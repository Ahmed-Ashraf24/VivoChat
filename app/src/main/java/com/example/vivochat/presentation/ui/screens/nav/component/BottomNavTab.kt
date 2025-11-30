package com.example.vivochat.presentation.ui.screens.nav.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.example.vivochat.presentation.ui.theme.Primary

@Composable
fun BottomNavTab(modifier: Modifier = Modifier,iconColor:Color,selectedIconColor:Color, icon: Painter,selectedIcon:Painter,name:String,selected: Boolean, onClick:()->Unit) {
    Tab(
        selectedContentColor =  Primary,
        unselectedContentColor = Color.White,
        selected = selected,
        onClick = onClick,
    ){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Icon(painter = if (selected)selectedIcon else icon,"icon", modifier = Modifier.size(24.dp), tint = if(selected) selectedIconColor else iconColor)
            AnimatedVisibility(selected) {
                Text(name,)
            }

        }

    }
}