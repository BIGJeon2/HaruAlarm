package com.jeon.model.DTO

import java.util.Calendar

data class CalendarDate(
    val calendarDate: Calendar,
    val dateID: String,
    var type: com.jeon.model.VO.DayType,
    var eventCount: Int,
    var description: String
)