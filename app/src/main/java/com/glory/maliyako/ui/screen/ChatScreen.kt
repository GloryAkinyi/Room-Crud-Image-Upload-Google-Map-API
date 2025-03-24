package com.glory.maliyako.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.glory.maliyako.viewmodel.ChatViewModel
import com.glory.maliyako.model.Message

@Composable
fun ChatScreen(chatViewModel: ChatViewModel = viewModel()) {
    var selectedTab by remember { mutableStateOf(0) }
    val messages by chatViewModel.messages.collectAsState()
    var messageText by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        // WhatsApp-style Tab Bar
        TabRow(selectedTabIndex = selectedTab) {
            listOf("Chats", "Status", "Calls").forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title, fontSize = 16.sp) }
                )
            }
        }

        when (selectedTab) {
            0 -> ChatTab(messages, messageText, { messageText = it }) {
                chatViewModel.sendMessage("Glory", messageText)
                messageText = ""
            }
            1 -> StatusTab()
            2 -> CallsTab()
        }
    }
}

// ✅ Chat Tab - Displays messages in WhatsApp style
@Composable
fun ChatTab(messages: List<Message>, messageText: String, onTextChange: (String) -> Unit, onSend: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            reverseLayout = true // New messages appear at the bottom
        ) {
            items(messages.reversed()) { message ->
                ChatBubble(message)
                Spacer(modifier = Modifier.height(8.dp)) // ✅ Added spacing between messages
            }
        }
        ChatInputField(messageText, onTextChange, onSend)
    }
}

// ✅ Chat Bubble (WhatsApp Style)
@Composable
fun ChatBubble(message: Message) {
    val isSentByUser = message.sender == "Glory"
    val bubbleColor = if (isSentByUser) Color(0xFFDCF8C6) else Color.White
    val alignment = if (isSentByUser) Alignment.End else Alignment.Start

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp), // ✅ Ensures alignment spacing
        horizontalArrangement = if (isSentByUser) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .background(bubbleColor, shape = RoundedCornerShape(12.dp))
                .padding(8.dp)
                .widthIn(max = 250.dp)
        ) {
            Column {
                Text(
                    text = message.text,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text(
                    text = "12:34 PM", // Placeholder for timestamp
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}

// ✅ WhatsApp-style Input Box
@Composable
fun ChatInputField(messageText: String, onTextChange: (String) -> Unit, onSend: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color(0xFFF0F0F0), RoundedCornerShape(20.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        BasicTextField(
            value = messageText,
            onValueChange = onTextChange,
            textStyle = TextStyle(fontSize = 16.sp),
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        )
        Button(
            onClick = { if (messageText.isNotBlank()) onSend() },
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text("Send")
        }
    }
}

// ✅ Placeholder Tabs (Status, Calls)
@Composable
fun StatusTab() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Status Screen", fontSize = 20.sp, color = Color.Gray)
    }
}

@Composable
fun CallsTab() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Calls Screen", fontSize = 20.sp, color = Color.Gray)
    }
}
