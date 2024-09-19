package com.jeon.harualarm.service

import com.jeon.harualarm.database.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("getVilageFcst?serviceKey=${ApiKey.ENCODING_KEY}")
    suspend fun getWeather(
        @Query("dataType") dataType : String,
        @Query("numOfRows") numOfRows : Int,
        @Query("pageNo") pageNo : Int,
        @Query("base_date") baseDate : Int,
        @Query("base_time") baseTime : Int,
        @Query("nx") nx : String,
        @Query("ny") ny : String
    ) : Response<Weather>
}