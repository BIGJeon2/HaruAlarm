package com.jeon.rest_api.client

import androidx.core.os.BuildCompat
import com.jeon.rest_api.BuildConfig
import com.jeon.rest_api.model.HolidayResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HolidayService {
    @GET("B090041/openapi/service/SpcdeInfoService/getHoliDeInfo")
    fun getHolidays(
        @Query("solYear") year: Int,
        @Query("ServiceKey", encoded = true) serviceKey: String = BuildConfig.API_KEY,
        @Query("_type") type: String = "json",
        @Query("numOfRows") row: Int = 100
    ): Call<HolidayResponse>
}