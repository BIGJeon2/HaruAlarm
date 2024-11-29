package com.jeon.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jeon.database.dto.HolidayDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface HolidayDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHoliday(holidayDTO: HolidayDTO)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllHolidays(holidayDTOList: List<HolidayDTO>)

    @Query("SELECT * FROM holidays")
    fun getAllHolidays(): Flow<List<HolidayDTO>>

    @Query("SELECT COUNT(*) FROM holidays")
    fun getAllHolidaysCount(): Int

    @Delete
    fun deletedHoliday(holidayDTO: HolidayDTO)

    @Update
    fun updateEvent(holidayDTO: HolidayDTO)

    @Query("SELECT * FROM holidays WHERE date = :dateID")
    fun getHoliday(dateID: String): HolidayDTO?

}