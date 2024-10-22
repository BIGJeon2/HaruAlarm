package com.jeon.harualarm.util

import android.annotation.SuppressLint
import com.jeon.harualarm.api.model.DayType
import com.jeon.harualarm.database.model.DTO.CalenderDate
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.util.ArrayList
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
    fun getDateToString(date: Calendar): String{
        val dateFormatted = String.format(
            "%04d%02d%02d",
            date.get(Calendar.YEAR),
            date.get(Calendar.MONTH) + 1, // MONTH는 0부터 시작하므로 1을 더함
            date.get(Calendar.DAY_OF_MONTH)
        )
        return dateFormatted
    }

}