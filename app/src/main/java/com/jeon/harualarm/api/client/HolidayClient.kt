package com.jeon.harualarm.api.client

import com.jeon.harualarm.api.HolidayAPI
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

object HolidayClient {
    private const val HOLIDAY_BASE_URL = "http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/" // 실제 API 베이스 URL로 변경

    val client: Retrofit = Retrofit.Builder()
        .baseUrl(HOLIDAY_BASE_URL)
        .addConverterFactory(SimpleXmlConverterFactory.create())
        .build()

    val holidayAPI: HolidayAPI = client.create(HolidayAPI::class.java)
}