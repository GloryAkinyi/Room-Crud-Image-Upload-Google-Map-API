package com.glory.maliyako.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.glory.maliyako.model.Message
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Query("SELECT * FROM messages ORDER BY timestamp ASC")
    fun getAllMessages(): Flow<List<Message>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMessage(message: Message)

    @Query("DELETE FROM messages")
    suspend fun clearMessages()
}
