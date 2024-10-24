package com.jeon.harualarm.util

import com.jeon.harualarm.model.DTO.Time
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class DateConverter {

    /**
     * @param date -> Calendar
     */
    fun dateID(date: Calendar): String{
        return SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(date.time)
    }

    fun getMonth(calendar: Calendar): Int {
        return calendar.get(Calendar.MONTH) + 1
    }

    fun getYear(calendar: Calendar): Int {
        return calendar.get(Calendar.YEAR)
    }

    fun getDay(calendar: Calendar): Int{
        return calendar.get(Calendar.DATE)
    }

    /**
     * @param date -> Calendar
     * @return String("yyyy.MM.dd.hh:mm")
     */
    fun dateToString(date: Calendar): String{
        return SimpleDateFormat("yyyy.MM.dd.hh:mm", Locale.KOREA).format(date.time)
    }

    /**
     * @param date -> SimpleDateFormat("yyyy.MM.dd.hh:mm")
     * @return Calendar
     */
    fun stringToDate(date: String): Calendar{
        val splitDate = date.split(".")
        val year = splitDate[0].toInt()
        val month = splitDate[1].toInt() - 1 // MONTH는 0부터 시작하므로 1을 빼줌
        val day = splitDate[2].toInt()
        val (hour, minute) = splitDate[2].split(":").map { it.toInt() }
        return Calendar.getInstance().apply {
            set(year, month, day, hour, minute, 0)
        }
    }

}