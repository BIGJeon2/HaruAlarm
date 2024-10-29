package com.jeon.rest_api.repository

import com.jeon.rest_api.client.HolidayService
import javax.inject.Inject

class HolidayApiRepository @Inject constructor(
    private val holidayService: HolidayService
) {
    suspend fun getAllHoliday(year: Int) =
        holidayService.getHolidays(year)

}