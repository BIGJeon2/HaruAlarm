package com.jeon.harualarm.database.model.DAO

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jeon.harualarm.database.model.DTO.Holiday
import com.jeon.harualarm.database.model.DTO.TodoEvent

interface HolidayDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHoliday(holiday: Holiday)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllHolidays(holidayList: List<Holiday>)

    @Delete
    suspend fun deletedEvent(holiday: Holiday)

    @Update
    suspend fun updateEvent(holiday: Holiday)

    @Query("SELECT * FROM holidays WHERE date = :date")
    suspend fun getHoliday(date: String): Holiday

}