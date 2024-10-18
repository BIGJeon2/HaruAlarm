package com.jeon.harualarm.api.client

import com.jeon.harualarm.api.HolidayAPI
import com.jeon.harualarm.api.model.HolidayResponse
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object HolidayClient {
    private const val HOLIDAY_BASE_URL = "http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/" // 실제 API 베이스 URL로 변경

    private val holidayClient: Retrofit = Retrofit.Builder()
        .baseUrl(HOLIDAY_BASE_URL)
        .client(OkHttpClient())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(TikXmlConverterFactory.create(TikXml.Builder().exceptionOnUnreadXml(false).build()))
        .build()

    val holidayAPI: HolidayAPI = holidayClient.create(HolidayAPI::class.java)
}