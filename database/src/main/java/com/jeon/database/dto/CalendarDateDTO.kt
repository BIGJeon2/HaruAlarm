package com.jeon.database.dto

import com.jeon.database.vo.DayType
import java.util.Calendar

data class CalendarDateDTO(
    val calendarDate: Calendar,
    val dateID: String,
    var type: DayType,
    var description: String,
    var todoList: List<TodoEventDTO>
)