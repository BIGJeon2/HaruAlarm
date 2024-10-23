package com.jeon.harualarm.database.model.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jeon.harualarm.database.model.DTO.TodoEvent
import retrofit2.http.GET

@Dao
interface EventDAO {
    @Insert
    suspend fun insertEvent(event: TodoEvent)

    @Delete
    suspend fun deletedEvent(event: TodoEvent)

    @Update
    suspend fun updateEvent(event: TodoEvent)

    @Query("SELECT * FROM todo_event_table WHERE date_id = :dateID")
    suspend fun getEvent(dateID: String): List<TodoEvent>

}