package com.jeon.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jeon.database.dto.TodoEventDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoEventDao {
    @Insert
    fun insertEvent(event: TodoEventDTO)

    @Delete
    fun deletedEvent(event: TodoEventDTO)

    @Update
    fun updateEvent(event: TodoEventDTO)

    @Query("SELECT * FROM todo_event_table")
    fun getAllEvent(): Flow<List<TodoEventDTO>>

    @Query("SELECT * FROM todo_event_table WHERE date_id = :dateID")
    fun getEvent(dateID: String): List<TodoEventDTO>

    @Query("SELECT COUNT(*) FROM todo_event_table WHERE date_id = :dateID")
    fun getEventSize(dateID: String): Int
}