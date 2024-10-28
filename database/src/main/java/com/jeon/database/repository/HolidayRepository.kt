package com.jeon.database.repository

import androidx.annotation.WorkerThread
import com.jeon.database.dao.HolidayDao
import com.jeon.database.Entity.Holiday

class HolidayRepository(private val holidayDAO: HolidayDao) {

    val getAllHolidays = holidayDAO.getAllHolidays()

    @WorkerThread
    suspend fun insertHoliday(holiday: Holiday) = holidayDAO.insertHoliday(holiday)

    @WorkerThread
    suspend fun insertAllHolidays(holidayList: List<Holiday>) = holidayDAO.insertAllHolidays(holidayList)

    @WorkerThread
    suspend fun getAllHolidaysCount(): Int = holidayDAO.getAllHolidaysCount()

    @WorkerThread
    suspend fun deletedHoliday(holiday: Holiday) = holidayDAO.deletedHoliday(holiday)

    @WorkerThread
    suspend fun updateEvent(holiday: Holiday) = holidayDAO.updateEvent(holiday)

    @WorkerThread
    suspend fun getHoliday(dateID: String): Holiday? = holidayDAO.getHoliday(dateID)

}