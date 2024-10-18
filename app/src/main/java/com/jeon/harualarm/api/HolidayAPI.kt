package com.jeon.harualarm.api

import com.jeon.harualarm.api.keys.HolidayAPIKey
import com.jeon.harualarm.api.model.HolidayResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HolidayAPI {

    @GET("getHoliDeInfo?")
    suspend fun getHolidays(
        @Query("solYear") year: Int,
        @Query("solMonth") month: Int,
        @Query("ServiceKey") serviceKey: String = HolidayAPIKey.ENCODING_KEY
    ): retrofit2.Call<HolidayResponse>

}