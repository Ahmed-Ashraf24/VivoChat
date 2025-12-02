package com.example.vivochat.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vivochat.domain.entity.Message
import com.example.vivochat.domain.repository.IMessageRep

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MessageViewModel(private val messageRepo: IMessageRep): ViewModel() {
    private var _messageData = MutableStateFlow<List<Message>>(emptyList())
    val messageData : StateFlow<List<Message>> =_messageData
    fun sendMessage(message: Message,reciverId:String){
        viewModelScope.launch {
            Log.d("message from MessageViewmodel",message.toString())
        messageRepo.sendMessage(message = message,reciverId)
    }
    }
    fun getMessages(userId:String,reciverId:String){
        viewModelScope.launch {
            messageRepo.getMessages(userId,reciverId).collect { messageList->
                _messageData.value=messageList
            }
        }
    }
}