package com.jeon.rest_api.client

import com.jeon.rest_api.BuildConfig
import com.jeon.rest_api.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("getVilageFcst?serviceKey=${BuildConfig.API_KEY}")
    suspend fun getWeather(
        @Query("dataType") dataType : String,
        @Query("numOfRows") numOfRows : Int,
        @Query("pageNo") pageNo : Int,
        @Query("base_date") baseDate : Int,
        @Query("base_time") baseTime : Int,
        @Query("nx") nx : String,
        @Query("ny") ny : String
    ) : Response<WeatherResponse>
}