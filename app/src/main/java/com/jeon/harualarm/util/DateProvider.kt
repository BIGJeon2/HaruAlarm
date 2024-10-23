package com.jeon.harualarm.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class DateProvider {
    
    fun getBeforeMonth(date: Date): String {
        val newDate = Calendar.getInstance().apply {
            time = date // Date 객체를 Calendar에 설정
            add(Calendar.MONTH, -1) // 한 달 전으로 계산
        }
        return getMonthToString(newDate.time) // Calendar에서 Date로 변환
    }

    fun getNextMonth(date: Date): String {
        val newDate = Calendar.getInstance().apply {
            time = date // Date 객체를 Calendar에 설정
            add(Calendar.MONTH, 1) // 한 달 후로 계산
        }
        return getMonthToString(newDate.time) // Calendar에서 Date로 변환
    }

    fun getYearToString(date: Date): String {
        return SimpleDateFormat("yyyy", Locale.KOREA).format(date)
    }

    fun getMonthToString(date: Date): String {
        return SimpleDateFormat("MM", Locale.KOREA).format(date)
    }

    fun getDayToString(date: Date): String {
        return SimpleDateFormat("dd", Locale.KOREA).format(date)
    }

    fun getFullDateToString(date: Date): String {
        return SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(date)
    }

    @SuppressLint("DefaultLocale")
    fun getDateID(date: Calendar): String{
        val dateFormatted = String.format(
            "%04d%02d%02d",
            date.get(Calendar.YEAR),
            date.get(Calendar.MONTH) + 1, // MONTH는 0부터 시작하므로 1을 더함
            date.get(Calendar.DAY_OF_MONTH),
        )
        return dateFormatted
    }

    @SuppressLint("DefaultLocale")
    fun getDateToString(date: Calendar): String{
        val dateFormatted = String.format(
            "%04d%02d%02d%2d%2d",
            date.get(Calendar.YEAR),
            date.get(Calendar.MONTH) + 1, // MONTH는 0부터 시작하므로 1을 더함
            date.get(Calendar.DAY_OF_MONTH),
            date.get(Calendar.HOUR_OF_DAY),
            date.get(Calendar.MINUTE)
        )
        return dateFormatted
    }

    @SuppressLint("DefaultLocale")
    fun getStringToCalendar(dateString: String): Calendar {
        val year = dateString.substring(0, 4).toInt()
        val month = dateString.substring(4, 6).toInt() - 1 // MONTH는 0부터 시작하므로 1을 빼줌
        val day = dateString.substring(6, 8).toInt()
        val hour = dateString.substring(8, 10).toInt()
        val minute = dateString.substring(10, 12).toInt()

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minute, 0) // 초는 0으로 설정
        calendar.set(Calendar.MILLISECOND, 0) // 밀리초는 0으로 설정

        return calendar
    }

}