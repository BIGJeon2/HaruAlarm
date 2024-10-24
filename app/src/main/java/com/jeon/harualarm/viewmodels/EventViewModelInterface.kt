package com.jeon.harualarm.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.jeon.harualarm.database.model.DTO.Event
import com.jeon.harualarm.model.DTO.CalendarDate
import com.jeon.harualarm.util.DateConverter
import com.jeon.harualarm.util.DateProvider
import java.util.Calendar

interface EventViewModelInterface {
    var dateConverter: DateConverter
    var dateProvider: DateProvider
    var date: MutableState<Calendar>
    var eventList: SnapshotStateList<Event>
    fun getEvent(date: Calendar)
    fun addEvent(date: Calendar, event: Event)
    fun deleteEvent(event: Event)
    fun updateEvent(event: Event)
}