package com.jeon.harualarm.database.model.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jeon.harualarm.database.model.DTO.CalenderDate
import com.jeon.harualarm.database.model.DTO.TodoEvent

@Dao
interface CalendarDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCalendarDate(calendarDTO: CalenderDate)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(todoEvent: TodoEvent)

    @Query("SELECT * FROM calendar_dates")
    suspend fun getAllCalendarDates(): List<CalenderDate>

    @Query("SELECT * FROM period_event WHERE calendarDateId = :calendarDateId")
    suspend fun getEventsForDate(calendarDateId: Long): List<TodoEvent>

    @Update
    suspend fun updateEvent(todoEvent: TodoEvent)

    @Delete
    suspend fun deletedEvent(todoEvent: TodoEvent)

}