package com.jeon.harualarm

import com.jeon.database.Entity.TodoEvent
import java.util.Calendar

data class CalendarDate(
    val calendarDate: Calendar,
    val dateID: String,
    var type: com.jeon.model.vo.DayType,
    var description: String,
    var todoList: List<TodoEvent>
)