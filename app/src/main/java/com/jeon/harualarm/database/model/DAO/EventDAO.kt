package com.jeon.harualarm.database.model.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jeon.harualarm.database.model.DTO.Event

@Dao
interface EventDAO {
    @Insert
    suspend fun insertEvent(event: Event)

    @Delete
    suspend fun deletedEvent(event: Event)

    @Update
    suspend fun updateEvent(event: Event)

    @Query("SELECT * FROM todo_event_table WHERE date_id = :dateID")
    suspend fun getEvent(dateID: String): List<Event>

    @Query("SELECT COUNT(*) FROM todo_event_table WHERE date_id = :dateID")
    suspend fun getEventSize(dateID: String): Int
}