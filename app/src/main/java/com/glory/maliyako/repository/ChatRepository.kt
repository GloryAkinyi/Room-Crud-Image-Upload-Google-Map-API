package com.glory.maliyako.repository

import com.glory.maliyako.data.MessageDao
import com.glory.maliyako.model.Message
import kotlinx.coroutines.flow.Flow

class ChatRepository(private val messageDao: MessageDao) {
    fun getAllMessages(): Flow<List<Message>> = messageDao.getAllMessages()
    suspend fun insertMessage(message: Message) = messageDao.insertMessage(message)
    suspend fun clearMessages() = messageDao.clearMessages() // Added for completeness
}
