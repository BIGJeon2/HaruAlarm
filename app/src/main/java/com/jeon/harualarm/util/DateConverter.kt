package com.jeon.harualarm.util

import com.jeon.harualarm.model.DTO.Time
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DateConverter {
    fun dateToString(date: Calendar): String{
        return SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(date)
    }

    fun timeToString(time: Time): String{
        return SimpleDateFormat("hh:mm", Locale.KOREA).format(time)
    }

}