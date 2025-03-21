package com.glory.maliyako.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glory.maliyako.model.Message
import com.glory.maliyako.repository.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel(private val repository: ChatRepository) : ViewModel() {

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages = _messages.asStateFlow()

    init {
        loadMessages()
    }

    private fun loadMessages() {
        viewModelScope.launch {
            repository.getAllMessages().collect { messageList ->
                _messages.value = messageList
            }
        }
    }

    fun sendMessage(sender: String, text: String) {
        val newMessage = Message(sender = sender, text = text, timestamp = System.currentTimeMillis())
        viewModelScope.launch {
            repository.insertMessage(newMessage)
        }
    }
}
