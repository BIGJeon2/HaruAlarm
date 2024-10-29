package com.jeon.harualarm.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.jeon.database.Entity.TodoEvent
import com.jeon.harualarm.util.DateConverter
import com.jeon.harualarm.util.DateProvider
import java.util.Calendar

interface EventViewModelInterface {
    var date: MutableState<Calendar>
    var eventList: SnapshotStateList<TodoEvent>
    fun getEvent(date: Calendar)
    fun addEvent(date: Calendar, event: TodoEvent)
    fun deleteEvent(event: TodoEvent)
    fun updateEvent(event: TodoEvent)
}