package com.jeon.harualarm.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.jeon.harualarm.database.model.DTO.CalendarDate
import com.jeon.harualarm.database.model.DTO.Holiday
import com.jeon.harualarm.util.DateProvider
import java.util.Calendar

interface CalendarViewModelInterface {
    var dateProvider: DateProvider
    var currDate: MutableState<Calendar>
    var selectedDate: MutableState<Calendar>
    var dayList: SnapshotStateList<CalendarDate>
    fun setNextMonth()
    fun setBeforeMonth()
    fun setSelectedDate(date: Calendar)
    fun setDayList()
}