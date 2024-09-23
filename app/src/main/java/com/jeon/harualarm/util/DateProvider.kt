package com.jeon.harualarm.util

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
        return SimpleDateFormat("yyyy.MM.dd", Locale.KOREA).format(date)
    }
}