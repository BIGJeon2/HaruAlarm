package com.jeon.harualarm.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeon.harualarm.api.model.VO.DayType
import com.jeon.harualarm.database.model.DAO.EventDAO
import com.jeon.harualarm.database.model.DAO.HolidayDAO
import com.jeon.harualarm.model.DTO.CalendarDate
import com.jeon.harualarm.database.model.DTO.Holiday
import com.jeon.harualarm.database.model.DTO.Event
import com.jeon.harualarm.database.model.VO.Type
import com.jeon.harualarm.util.DateConverter
import com.jeon.harualarm.util.DateProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class CalendarViewModel(private val holidayRepository: HolidayDAO, private val jobDatabase: EventDAO): ViewModel(), CalendarViewModelInterface {
    override var dateConverter = DateConverter()
    override var dateProvider = DateProvider()
    override var currDate = mutableStateOf(Calendar.getInstance().apply { set(Calendar.DATE, 1) })
    override var selectedDate = mutableStateOf(Calendar.getInstance())
    override var dayList: SnapshotStateList<CalendarDate> = mutableStateListOf()
    override var todoList: SnapshotStateList<Event> = mutableStateListOf()

    init {
        setDayList()
    }

    override fun setDayList(){
        val calendar = currDate.value.clone() as Calendar
        viewModelScope.launch(Dispatchers.IO) {
            val days = ArrayList<CalendarDate>()

            //Add before date
            val beforeDate = dateProvider.getBeforeMonth(calendar)
            val beforeDaySize = calendar.get(Calendar.DAY_OF_WEEK) - 1
            val beforeDayList = dateProvider.getMonthDayList(beforeDate)
            for (idx in beforeDayList.size - beforeDaySize until beforeDayList.size){
                val newDate = beforeDayList[idx]
                val dateID = dateConverter.dateID(newDate)
                val holiday = holidayRepository.getHoliday(dateID)
                val type = if (holiday != null) DayType.HOLIDAY else {
                        if (newDate[7] == 1 || newDate[7] == 7) DayType.WEEKEND else DayType.WEEKDAY
                    }
                val eventList = jobDatabase.getEvent(dateConverter.dateID(newDate))
                val description = holiday?.description ?: ""
                days.add(CalendarDate(newDate, dateID, type, eventList, description))
            }

            //Add curr date
            val currDayList = dateProvider.getMonthDayList(calendar)
            for (newDate in currDayList){
                val dateID = dateConverter.dateID(newDate)
                val holiday = holidayRepository.getHoliday(dateID)
                val type = if (holiday != null) DayType.HOLIDAY else {
                    if (newDate[7] == 1 || newDate[7] == 7) DayType.WEEKEND else DayType.WEEKDAY
                }
                val eventList = jobDatabase.getEvent(dateConverter.dateID(newDate))
                val description = holiday?.description ?: ""
                days.add(CalendarDate(newDate, dateID, type, eventList, description))
            }

            //Add next date
            val nextDate = dateProvider.getNextMonth(calendar)
            val nextDateList = dateProvider.getMonthDayList(nextDate)
            for (idx in 0 until  35 - days.size){
                val newDate = nextDateList[idx]
                val dateID = dateConverter.dateID(newDate)
                val holiday = holidayRepository.getHoliday(dateID)
                val type = if (holiday != null) DayType.HOLIDAY else {
                    if (newDate[7] == 1 || newDate[7] == 7) DayType.WEEKEND else DayType.WEEKDAY
                }
                val eventList = jobDatabase.getEvent(dateConverter.dateID(newDate))
                val description = holiday?.description ?: ""
                days.add(CalendarDate(newDate, dateID, type, eventList, description))
            }
            dayList.clear()
            dayList.addAll(days)
        }
    }

    override fun setBeforeMonth() {
        currDate.value = dateProvider.getBeforeMonth(currDate.value)
        setDayList()
    }

    override fun setNextMonth() {
        currDate.value = dateProvider.getNextMonth(currDate.value)
        setDayList()
    }

    override fun setSelectedDate(date: Calendar) {
        selectedDate.value = date
        dayList.find { it.date == dateConverter.dateID(selectedDate.value)} ?.let {
            todoList.clear()
            todoList.addAll(it.todos)
        }
    }

    fun addTodoList(){
        val dateToString = dateConverter.dateToString(selectedDate.value)
        val dateID = dateConverter.dateID(selectedDate.value)
        val event = Event(dateToString, Type.DAY, "", dateToString, dateToString, true, 30L, dateID)
        viewModelScope.launch(Dispatchers.IO){
            jobDatabase.insertEvent(event)
            todoList.clear()
            todoList.addAll(jobDatabase.getEvent(dateID))
            dayList.find { it.date == dateID }?.todos = todoList
        }
    }

    fun deleteTodo(todo: Event) {
        viewModelScope.launch(Dispatchers.IO) {
            jobDatabase.deletedEvent(todo)
            todoList.remove(todo)
        }
    }
}