package com.example.vivochat.presentation.view.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vivochat.R
import com.example.vivochat.domain.entity.User
import com.example.vivochat.presentation.ui.screens.Contacts.ContactsRoute
import com.example.vivochat.presentation.ui.screens.home.viewmodel.HomeViewModel

@Composable
fun HomeHeader(
    viewModel: HomeViewModel,
    navController: NavController,
    onStoryClicked:(User)->Unit,
) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 7.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
        ) {
             Text(viewModel.user.fullName, fontSize = 24.sp, fontWeight = FontWeight.Medium)

        }
        Box(
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
                .clickable {

                    navController.navigate(ContactsRoute)
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("unAvailableContacts", viewModel.unAvailableContacts)
                }
                .background(color = Color(0xFFEEEEEE), shape = RoundedCornerShape(12.dp))
        ) {
            Image(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(22.dp),
                painter = painterResource(R.drawable.addcontact), contentDescription = null
            )
        }
    }
    Spacer(Modifier.height(10.dp))
     StoryItem(viewModel,onStoryClicked)
}