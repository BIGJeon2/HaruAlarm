package com.jeon.harualarm.database.model.DTO

import com.jeon.harualarm.api.model.DayType
import java.util.Calendar

data class CalendarDate(
    val calendarDate: Calendar,
    val date: String,
    var type: DayType,
    var todos: List<TodoEvent>,
    var description: String
)