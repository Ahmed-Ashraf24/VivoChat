package com.example.vivochat.presentation.ui.screens.Contacts

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import com.example.vivochat.domain.entity.Contact
import com.example.vivochat.presentation.ui.screens.Contacts.components.ContactItem
import kotlinx.serialization.Serializable

@Serializable
data object ContactsRoute

@Composable
fun Contacts(
    unAvailableContacts: List<Contact>
) {
    Log.d("HEYYYYYY",unAvailableContacts.size.toString())
    LazyColumn(
        modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(top = 50.dp, start = 5.dp, end = 5.dp)

    ){

       item { Column()  {
           Text("Un Available contacts", color = MaterialTheme.colorScheme.onSurface, fontSize = 22.sp, fontWeight = FontWeight.Bold)
           Spacer(Modifier.height(10.dp))
       }}

        items(unAvailableContacts.size){
            ContactItem(unAvailableContacts[it])
            Spacer(Modifier.height(10.dp))
        }

    }
}