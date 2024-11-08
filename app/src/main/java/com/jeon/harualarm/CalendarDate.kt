package com.jeon.harualarm

import java.util.Calendar

data class CalendarDate(
    val calendarDate: Calendar,
    val dateID: String,
    var type: com.jeon.model.vo.DayType,
    var description: String
)