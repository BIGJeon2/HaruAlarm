package com.jeon.database.repository

import androidx.annotation.WorkerThread
import com.jeon.database.dao.HolidayDao
import com.jeon.database.dto.HolidayDTO

class HolidayRepository(private val holidayDAO: HolidayDao) {

    val getAllHolidays = holidayDAO.getAllHolidays()

    @WorkerThread
    suspend fun insertHoliday(holidayDTO: HolidayDTO) = holidayDAO.insertHoliday(holidayDTO)

    @WorkerThread
    suspend fun insertAllHolidays(holidayDTOList: List<HolidayDTO>) = holidayDAO.insertAllHolidays(holidayDTOList)

    @WorkerThread
    suspend fun getAllHolidaysCount(): Int = holidayDAO.getAllHolidaysCount()

    @WorkerThread
    suspend fun deletedHoliday(holidayDTO: HolidayDTO) = holidayDAO.deletedHoliday(holidayDTO)

    @WorkerThread
    suspend fun updateEvent(holidayDTO: HolidayDTO) = holidayDAO.updateEvent(holidayDTO)

    @WorkerThread
    suspend fun getHoliday(dateID: String): HolidayDTO? = holidayDAO.getHoliday(dateID)

}