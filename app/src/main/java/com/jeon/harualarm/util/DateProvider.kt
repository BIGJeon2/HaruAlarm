package com.jeon.harualarm.util

import android.annotation.SuppressLint
import com.jeon.harualarm.api.model.DayType
import com.jeon.harualarm.database.model.DTO.CalendarDate
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Calendar
import java.util.Date
import java.util.Locale

class DateProvider {
    /**
     * Get Date List
     */
    fun getDaysList(date: Calendar): List<CalendarDate>{
        val dayList = ArrayList<CalendarDate>()
        val beforeCalendar = date.clone() as Calendar
        beforeCalendar.add(Calendar.MONTH, -1)
        val currCalendar = date.clone() as Calendar
        val nextCalendar = date.clone() as Calendar
        nextCalendar.add(Calendar.MONTH, 1)

        //이전 달에 대한 데이터 추가
        val beforeDaysSize = currCalendar.get(Calendar.DAY_OF_WEEK) - 1
        val lastIndexBeforeDate = beforeCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        for (i in beforeDaysSize - 1 downTo 0 ){
            val beforeDate = beforeCalendar.clone() as Calendar
            beforeDate.set(Calendar.DATE, lastIndexBeforeDate - i)
            dayList.add(
                CalendarDate(
                    beforeDate,
                    getDateToString(beforeDate),
                    if (beforeDate[7] == 1 || beforeDate[7] == 7) DayType.WEEKEND else DayType.WEEKDAY,
                    ""
                )
            )
        }
        //현재 달에 대한 date 추가
        val currDateSize = currCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        for (i in 1 .. currDateSize){
            val currDate = currCalendar.clone() as Calendar
            currDate.set(Calendar.DATE, i)
            dayList.add(
                CalendarDate(
                    currDate,
                    getDateToString(currDate),
                    if (currDate[7] == 1 || currDate[7] == 7) DayType.WEEKEND else DayType.WEEKDAY,
                    ""
                )
            )
        }
        val nextDateSize = 35 - dayList.size
        for (i in 1 .. nextDateSize){
            val nextDate = nextCalendar.clone() as Calendar
            nextDate.set(Calendar.DATE, i)
            dayList.add(
                CalendarDate(
                    nextDate,
                    getDateToString(nextDate),
                    if (nextDate[7] == 1 || nextDate[7] == 7) DayType.WEEKEND else DayType.WEEKDAY,
                    ""
                )
            )
        }
        return dayList
    }
    
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