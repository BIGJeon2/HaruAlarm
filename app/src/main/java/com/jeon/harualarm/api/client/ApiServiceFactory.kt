package com.jeon.harualarm.api.client

import com.jeon.harualarm.api.HolidayAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiServiceFactory {
    private const val HOLIDAY_BASE_URL = "http://apis.data.go.kr/" // 실제 API 베이스 URL로 변경

    private val okHttpClient = OkHttpClient.Builder().apply {
        connectTimeout(60, TimeUnit.SECONDS)
        readTimeout(60, TimeUnit.SECONDS)
        writeTimeout(60, TimeUnit.SECONDS)
        addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
    }.build()

    private val holidayClient: Retrofit = Retrofit.Builder()
        .baseUrl(HOLIDAY_BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val holidayAPI: HolidayAPI = holidayClient.create(HolidayAPI::class.java)
}