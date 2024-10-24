package com.jeon.harualarm.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeon.harualarm.database.model.DAO.EventDAO
import com.jeon.harualarm.database.model.DTO.Event
import com.jeon.harualarm.model.DTO.CalendarDate
import com.jeon.harualarm.util.DateConverter
import com.jeon.harualarm.util.DateProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class EventViewModel(private val eventRepository: EventDAO): ViewModel(), EventViewModelInterface {
    override var dateConverter = DateConverter()
    override var dateProvider = DateProvider()
    override var date = mutableStateOf(Calendar.getInstance().apply { set(Calendar.DATE, 1) })
    override var eventList = SnapshotStateList<Event>()

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
            eventList.addAll(eventRepository.getEvent(dateConverter.dateID(date)))
        }
    }

    override fun addEvent(date: Calendar, event: Event) {
        viewModelScope.launch(Dispatchers.IO) {
            eventRepository.insertEvent(event)
            eventList.add(event)
        }
    }

    override fun deleteEvent(event: Event) {
        viewModelScope.launch(Dispatchers.IO) {
            eventRepository.deletedEvent(event)
            eventList.remove(event)
        }
    }

    override fun updateEvent(event: Event) {
        viewModelScope.launch(Dispatchers.IO) {
            eventRepository.updateEvent(event)
        }
    }

}