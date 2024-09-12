package com.jeon.harualarm.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DateProvider {
    fun getBeforeMonth(date: Calendar): String {
        val newDate = Calendar.getInstance().apply {
            time = date.time // 현재 선택된 날짜를 기반으로 새로운 달 계산
            add(Calendar.MONTH, -1)
        }
        return getMonthToString(newDate)
    }
    fun getNextMonth(date: Calendar): String {
        val newDate = Calendar.getInstance().apply {
            time = date.time // 현재 선택된 날짜를 기반으로 새로운 달 계산
            add(Calendar.MONTH, 1)
        }
        return getMonthToString(newDate)
    }
    fun getYearToString(date: Calendar): String{
        return SimpleDateFormat("yyyy", Locale.KOREA).format(date.time)
    }
    fun getMonthToString(date: Calendar): String{
        return SimpleDateFormat("MM", Locale.KOREA).format(date.time)
    }
    fun getDayToString(date: Calendar): String{
        return SimpleDateFormat("dd", Locale.KOREA).format(date.time)
    }
    fun getFullDateToString(date: Calendar): String{
        return SimpleDateFormat("yyyy.MM.dd", Locale.KOREA).format(date.time)
    }
}