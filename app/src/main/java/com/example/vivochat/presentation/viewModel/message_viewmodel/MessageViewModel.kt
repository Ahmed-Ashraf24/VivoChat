package com.example.vivochat.presentation.viewModel.message_viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vivochat.domain.entity.LastMessagePreview
import com.example.vivochat.domain.entity.Message
import com.example.vivochat.domain.entity.MessageType
import com.example.vivochat.domain.repository.IAuthRepo
import com.example.vivochat.domain.repository.IMessageRep
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(private val messageRepo: IMessageRep,
                                           private val authRepo: IAuthRepo): ViewModel() {
    private var _messageData = MutableStateFlow<List<Message>>(emptyList())
    val messageData : StateFlow<List<Message>> =_messageData
    private val _lastMessages = MutableStateFlow<Map<String, LastMessagePreview?>>(emptyMap())
    val lastMessages: StateFlow<Map<String, LastMessagePreview?>> = _lastMessages
    fun sendMessage(message: String, reciverId:String){
        viewModelScope.launch {
        messageRepo.sendMessage(message = Message(
            senderId = authRepo.getLoggedUserIdOrNull()!!,
            senderName ="ahmed",
            message = message,
            messageType = MessageType.MyMessage,
            messageDate = ""
        ),reciverId)
    }
    }
    fun getMessages(reciverId:String){
        viewModelScope.launch {
            messageRepo.getMessages(authRepo.getLoggedUserIdOrNull()!!,reciverId).collect { messageList->
                _messageData.value=messageList
            }
        }
    }
    fun getLastMessage(receiverId:String){
        viewModelScope.launch {
            messageRepo.getLastMessage(authRepo.getLoggedUserIdOrNull()!!,receiverId).collect { lastMessage->
                Log.d("get last message",lastMessage.message)
                val updated = _lastMessages.value.toMutableMap()
                updated[receiverId] = lastMessage

                _lastMessages.value = updated
            }
        }
    }
}