package com.jeon.harualarm.util

import com.jeon.harualarm.model.DTO.Time
import java.text.SimpleDateFormat
import java.util.Locale

class TimeConverter {
    fun timeToString(time: Time): String{
        return SimpleDateFormat("hh:mm", Locale.KOREA).format(time)
    }
}