package com.jeon.harualarm.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeon.database.dto.TodoEventDTO
import com.jeon.database.repository.TodoEventRepository
import com.jeon.harualarm.util.DateConverter
import com.jeon.harualarm.util.DateProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(private val eventRepository: TodoEventRepository): ViewModel(), EventViewModelInterface {
    var dateConverter = DateConverter()
    var dateProvider = DateProvider()
    override var date = mutableStateOf(Calendar.getInstance().apply { set(Calendar.DATE, 1) })
    override var eventList = SnapshotStateList<TodoEventDTO>()

    fun setNextDate(){
        val newDate = (date.value.clone() as Calendar).apply {
            add(Calendar.DATE, 1)
        }
        date.value = newDate
    }

    fun setBeforeDate(){
        val newDate = (date.value.clone() as Calendar).apply {
            add(Calendar.DATE, -1)
        }
        date.value = newDate
    }

    override fun getEvent(date: Calendar) {
        viewModelScope.launch(Dispatchers.IO) {
            eventList.clear()
            val todoEventList = eventRepository.getEventList(dateConverter.dateID(date))
            eventList.addAll(todoEventList)
        }
    }

    override fun addEvent(date: Calendar, event: TodoEventDTO) {
        viewModelScope.launch(Dispatchers.IO) {
            eventRepository.insertEvent(event)
            eventList.add(event)
        }
    }

    override fun deleteEvent(event: TodoEventDTO) {
        viewModelScope.launch(Dispatchers.IO) {
            eventRepository.deletedEvent(event)
            eventList.remove(event)
        }
    }

    override fun updateEvent(event: TodoEventDTO) {
        viewModelScope.launch(Dispatchers.IO) {
            eventRepository.updateEvent(event)
        }
    }

}