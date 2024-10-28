package com.jeon.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jeon.database.Entity.TodoEvent
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoEventDao {
    @Insert
    fun insertEvent(event: TodoEvent)

    @Delete
    fun deletedEvent(event: TodoEvent)

    @Update
    fun updateEvent(event: TodoEvent)

    @Query("SELECT * FROM todo_event_table")
    fun getAllEvent(): Flow<List<TodoEvent>>

    @Query("SELECT * FROM todo_event_table WHERE date_id = :dateID")
    fun getEvent(dateID: String): Flow<List<TodoEvent>>

    @Query("SELECT COUNT(*) FROM todo_event_table WHERE date_id = :dateID")
    fun getEventSize(dateID: String): Int
}