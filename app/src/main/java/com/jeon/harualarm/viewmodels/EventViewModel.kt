package com.jeon.harualarm.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeon.database.Entity.TodoEvent
import com.jeon.database.repository.TodoEventRepository
import com.jeon.harualarm.util.DateConverter
import com.jeon.harualarm.util.DateProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class EventViewModel(private val eventRepository: TodoEventRepository): ViewModel(), EventViewModelInterface {
    override var dateConverter = DateConverter()
    override var dateProvider = DateProvider()
    override var date = mutableStateOf(Calendar.getInstance().apply { set(Calendar.DATE, 1) })
    override var eventList = SnapshotStateList<TodoEvent>()

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
            eventRepository.getEventList(dateConverter.dateID(date)).collect{ event ->
                eventList.addAll(event)
            }
        }
    }

    override fun addEvent(date: Calendar, event: TodoEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            eventRepository.insertEvent(event)
            eventList.add(event)
        }
    }

    override fun deleteEvent(event: TodoEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            eventRepository.deletedEvent(event)
            eventList.remove(event)
        }
    }

    override fun updateEvent(event: TodoEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            eventRepository.updateEvent(event)
        }
    }

}