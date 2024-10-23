package com.jeon.harualarm.api.client

import com.jeon.harualarm.api.keys.HolidayAPIKey
import com.jeon.harualarm.api.model.DTO.Holidays
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HolidayService {
    @GET("B090041/openapi/service/SpcdeInfoService/getHoliDeInfo")
    fun getHolidays(
        @Query("solYear") year: Int,
        @Query("ServiceKey", encoded = true) serviceKey: String = HolidayAPIKey.ENCODING_KEY,
        @Query("_type") type: String = "json",
        @Query("numOfRows") row: Int = 100
    ): Call<Holidays>
}