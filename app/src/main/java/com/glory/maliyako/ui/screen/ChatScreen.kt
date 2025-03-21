package com.glory.maliyako.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.glory.maliyako.viewmodel.ChatViewModel
import com.glory.maliyako.model.Message
import com.glory.maliyako.repository.ChatRepository
import com.glory.maliyako.viewmodel.ChatViewModelFactory

@Composable
fun ChatScreen(chatViewModel: ChatViewModel = viewModel()) {
    val messages by chatViewModel.messages.collectAsState()
    var messageText by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(messages) { message ->
                Text(text = "${message.sender}: ${message.text}")
            }
        }

        Row(modifier = Modifier.padding(8.dp)) {
            BasicTextField(
                value = messageText,
                onValueChange = { messageText = it },
                modifier = Modifier.weight(1f)
            )
            Button(onClick = {
                chatViewModel.sendMessage("Glory", messageText)
                messageText = ""
            }) {
                Text("Send")
            }
        }
    }
}
