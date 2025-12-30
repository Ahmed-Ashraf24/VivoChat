package com.example.vivochat.presentation.ui.screens.chat.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vivochat.domain.entity.Message
import com.example.vivochat.domain.entity.MessageType
import com.example.vivochat.domain.repository.IAuthRepo
import com.example.vivochat.domain.repository.IMessageRep
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val messageRepo: IMessageRep,
    private val authRepo: IAuthRepo
) : ViewModel() {
    private var _messageData = MutableStateFlow<List<Message>>(emptyList())
    val messageData: StateFlow<List<Message>> = _messageData
    private var _isOnline=MutableStateFlow<Boolean>(false)
    val isOnline=_isOnline.asStateFlow()
    fun sendMessage(message: String, receiver: String) {
        viewModelScope.launch {
            messageRepo.sendMessage(
                message = Message(
                    senderId = authRepo.getLoggedUserIdOrNull()!!,
                    senderName = "ahmed",
                    message = message,
                    messageType = MessageType.MyMessage,
                    messageDate = ""
                ), receiver
            )
        }
    }

    fun getMessages(receiverId: String) {
        viewModelScope.launch {
            messageRepo.setStateI(authRepo.getLoggedUserIdOrNull()!!)

            messageRepo.getMessages(authRepo.getLoggedUserIdOrNull()!!, receiverId)
                .collect { messageList ->
                    _messageData.value = messageList
                }

        }

    }
    fun getState(receiverId: String){
        viewModelScope.launch {
            messageRepo.observeUser(receiverId).collect {userPresence ->
                _isOnline.value=userPresence.online
            }
        }
    }

}