package com.jeon.harualarm.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.jeon.harualarm.database.model.DTO.Event
import com.jeon.harualarm.model.DTO.CalendarDate
import com.jeon.harualarm.util.DateConverter
import com.jeon.harualarm.util.DateProvider
import java.util.Calendar

interface CalendarViewModelInterface {
    var dateConverter: DateConverter
    var dateProvider: DateProvider
    var currDate: MutableState<Calendar>
    var selectedDate: MutableState<Calendar>
    var dayList: SnapshotStateList<CalendarDate>
    var todoList: SnapshotStateList<Event>
    fun setNextMonth()
    fun setBeforeMonth()
    fun setSelectedDate(date: Calendar)
    fun setDayList()
}