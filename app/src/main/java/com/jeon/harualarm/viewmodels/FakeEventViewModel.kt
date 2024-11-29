package com.jeon.harualarm.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.jeon.database.dto.TodoEventDTO
import com.jeon.harualarm.util.DateConverter
import com.jeon.harualarm.util.DateProvider
import com.jeon.database.vo.EventType
import java.util.Calendar

class FakeEventViewModel : ViewModel(), EventViewModelInterface {
    override var date = mutableStateOf(Calendar.getInstance().apply { set(Calendar.DATE, 1) })
    override var eventList: SnapshotStateList<TodoEventDTO> = mutableStateListOf()
    var dateConverter: DateConverter = DateConverter()
    var dateProvider: DateProvider = DateProvider()


    init {
        for (i in 1 .. 10){
            val date = Calendar.getInstance().apply {
                set(Calendar.DATE, i)
            }
            eventList.add(
                TodoEventDTO(
                "Title$i",
                EventType.DAY,
                "Description",
                DateConverter().dateToString(date),
                DateConverter().dateToString(date),
                true,
                30L,
                DateConverter().dateID(date)
            )
            )
        }

    }
    override fun getEvent(date: Calendar) {
        TODO("Not yet implemented")
    }

    override fun addEvent(date: Calendar, event: TodoEventDTO) {
        TODO("Not yet implemented")
    }

    override fun deleteEvent(event: TodoEventDTO) {
        TODO("Not yet implemented")
    }

    override fun updateEvent(event: TodoEventDTO) {
        TODO("Not yet implemented")
    }
}