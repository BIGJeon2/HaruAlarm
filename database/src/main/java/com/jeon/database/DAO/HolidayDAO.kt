package com.jeon.database.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jeon.database.Entity.Holiday
import kotlinx.coroutines.flow.Flow

@Dao
interface HolidayDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHoliday(holiday: Holiday)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllHolidays(holidayList: List<Holiday>)

    @Query("SELECT * FROM holidays")
    fun getAllHolidays(): Flow<List<Holiday>>

    @Query("SELECT COUNT(*) FROM holidays")
    fun getAllHolidaysCount(): Int

    @Delete
    fun deletedHoliday(holiday: Holiday)

    @Update
    fun updateEvent(holiday: Holiday)

    @Query("SELECT * FROM holidays WHERE date = :dateID")
    fun getHoliday(dateID: String): Holiday?

}