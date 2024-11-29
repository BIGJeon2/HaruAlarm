package com.jeon.harualarm.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.jeon.database.dto.TodoEventDTO
import java.util.Calendar

interface EventViewModelInterface {
    var date: MutableState<Calendar>
    var eventList: SnapshotStateList<TodoEventDTO>
    fun getEvent(date: Calendar)
    fun addEvent(date: Calendar, event: TodoEventDTO)
    fun deleteEvent(event: TodoEventDTO)
    fun updateEvent(event: TodoEventDTO)
}