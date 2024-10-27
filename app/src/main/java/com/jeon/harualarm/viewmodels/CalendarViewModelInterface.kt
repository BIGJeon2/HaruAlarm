package com.jeon.harualarm.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.jeon.model.DTO.CalendarDate
import com.jeon.harualarm.util.DateConverter
import com.jeon.harualarm.util.DateProvider
import java.util.Calendar

interface CalendarViewModelInterface {
    var dateConverter: DateConverter
    var dateProvider: DateProvider
    var currDate: MutableState<Calendar>
    var dayList: SnapshotStateList<com.jeon.model.DTO.CalendarDate>
    fun setNextMonth()
    fun setBeforeMonth()
    fun setDayList()
}