package com.jeon.harualarm.model.DTO

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.jeon.harualarm.api.model.VO.DayType
import com.jeon.harualarm.database.model.DTO.Event
import java.util.Calendar

data class CalendarDate(
    val calendarDate: Calendar,
    val date: String,
    var type: DayType,
    var todos: List<Event>,
    var description: String
)