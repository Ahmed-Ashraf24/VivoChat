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


}