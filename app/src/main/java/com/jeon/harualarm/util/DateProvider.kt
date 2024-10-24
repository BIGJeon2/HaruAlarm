package com.jeon.harualarm.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class DateProvider {
    
    fun getBeforeMonth(calendar: Calendar): Calendar {
        return (calendar.clone() as Calendar).apply { add(Calendar.MONTH, -1) }
    }

    fun getNextMonth(calendar: Calendar): Calendar {
        return (calendar.clone() as Calendar).apply { add(Calendar.MONTH, 1) }
    }

    fun getMonthDayList(calendar: Calendar): ArrayList<Calendar>{
        val days = ArrayList<Calendar>()
        val daySize = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        for (i in 1 .. daySize) {
            days.add((calendar.clone() as Calendar).apply {
                set(Calendar.DATE, i)
            })
        }
        return days
    }

    @SuppressLint("DefaultLocale")
    fun dateID(date: Calendar): String{
        val dateFormatted = String.format(
            "%04d%02d%02d",
            date.get(Calendar.YEAR),
            date.get(Calendar.MONTH) + 1, // MONTH는 0부터 시작하므로 1을 더함
            date.get(Calendar.DAY_OF_MONTH),
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