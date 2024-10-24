package com.jeon.harualarm.database.model.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jeon.harualarm.database.model.DTO.Holiday

@Dao
interface HolidayDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHoliday(holiday: Holiday)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllHolidays(holidayList: List<Holiday>)

    @Query("SELECT * FROM holidays")
    suspend fun getAllHolidays(): List<Holiday>?

    @Query("SELECT COUNT(*) FROM holidays")
    suspend fun getAllHolidaysCount(): Int

    @Delete
    suspend fun deletedEvent(holiday: Holiday)

    @Update
    suspend fun updateEvent(holiday: Holiday)

    @Query("SELECT * FROM holidays WHERE date = :date")
    suspend fun getHoliday(date: String): Holiday?

}