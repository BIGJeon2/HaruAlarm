package com.jeon.harualarm.model.DTO

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.jeon.harualarm.api.model.VO.DayType
import com.jeon.harualarm.database.model.DTO.Event
import java.util.Calendar

data class CalendarDate(
    val calendarDate: Calendar,
    val dateID: String,
    var type: DayType,
    var eventCount: Int,
    var description: String
)