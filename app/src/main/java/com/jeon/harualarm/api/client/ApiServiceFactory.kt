package com.jeon.harualarm.api.client

import com.jeon.harualarm.api.HolidayAPI
import com.jeon.harualarm.api.WeatherAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiServiceFactory {
    //공공 데이터 포탈 API 기본 경로(동일)
    private const val PORTAL_BASE_URL = "http://apis.data.go.kr/"

    private val okHttpClient = OkHttpClient.Builder().apply {
        connectTimeout(60, TimeUnit.SECONDS)
        readTimeout(60, TimeUnit.SECONDS)
        writeTimeout(60, TimeUnit.SECONDS)
        addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
    }.build()

    private val holidayClient: Retrofit = Retrofit.Builder()
        .baseUrl(PORTAL_BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val weatherClient: Retrofit = Retrofit.Builder()
        .baseUrl(PORTAL_BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val holidayAPI: HolidayAPI = holidayClient.create(HolidayAPI::class.java)
    val weatherAPI: WeatherAPI = weatherClient.create(WeatherAPI::class.java)
}