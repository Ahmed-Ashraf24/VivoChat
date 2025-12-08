package com.example.vivochat.presentation.viewModel.shared_view_model

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.example.vivochat.domain.entity.User
import com.google.firebase.Timestamp
import java.util.concurrent.TimeUnit
class SharedViewModel : ViewModel(){
    var selectedUser = mutableStateOf<User?>(null)

    fun sendUser(user:User){
        selectedUser.value = user
    }
    fun getRelativeTime(timestamp: Timestamp): String {
        val now = System.currentTimeMillis()
        val storyTime = timestamp.toDate().time
        val diff = now - storyTime

        val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)
        val hours = TimeUnit.MILLISECONDS.toHours(diff)
        val days = TimeUnit.MILLISECONDS.toDays(diff)

        return when {
            minutes < 1 -> "just now"
            minutes < 60 -> "${minutes}m"
            hours < 24 -> "${hours}h"
            days < 7 -> "${days}d"
            else -> java.text.SimpleDateFormat("dd/MM/yyyy").format(timestamp.toDate())
        }
    }

}