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
import com.jeon.harualarm.util.DateProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class CalendarViewModel(private val holidayRepository: HolidayDAO, private val jobDatabase: EventDAO): ViewModel(), CalendarViewModelInterface {
    override var dateProvider = DateProvider()
    override var currDate = mutableStateOf(Calendar.getInstance().apply {
        set(Calendar.DATE, 1)
    })
    override var selectedDate = mutableStateOf(Calendar.getInstance())
    override var dayList: SnapshotStateList<CalendarDate> = mutableStateListOf()
    var todoDTOList: SnapshotStateList<Event> = mutableStateListOf()
        private set

    init {
        setDayList()
    }

    fun setDayList(calendar: Calendar){
        viewModelScope.launch(Dispatchers.IO) {
            val days = ArrayList<CalendarDate>()
            val beforeDate = calendar.clone() as Calendar
            beforeDate.apply {
                add(Calendar.MONTH, -1)
            }
            val beforeDaySize = calendar.get(Calendar.DAY_OF_WEEK) - 1
            val maxOfBeforeDate = beforeDate.getActualMaximum(Calendar.DAY_OF_MONTH)
            for (i in maxOfBeforeDate - beforeDaySize + 1  .. maxOfBeforeDate){
                val date = beforeDate.clone() as Calendar
                date.apply {
                    set(Calendar.DATE, i)
                }
                val holiday: Holiday? = holidayRepository.getHoliday(dateProvider.getDateID(date))
                days.add(
                    CalendarDate(
                        date,
                        dateProvider.getDateID(date),
                        if (date[7] == 1 || date[7] == 7 || holiday != null) DayType.WEEKEND else DayType.WEEKDAY,
                        jobDatabase.getEvent(dateProvider.getDateID(date)),
                        holiday?.description ?: ""
                    )
                )
            }

            for (i in 1 .. calendar.getActualMaximum(Calendar.DAY_OF_MONTH)){
                val date = (calendar.clone() as Calendar).apply { set(Calendar.DATE, i) }
                val holiday: Holiday? = holidayRepository.getHoliday(dateProvider.getDateID(date))
                days.add(
                    CalendarDate(
                        date,
                        dateProvider.getDateID(date),
                        if (date[7] == 1 || date[7] == 7 || holiday != null) DayType.WEEKEND else DayType.WEEKDAY,
                        jobDatabase.getEvent(dateProvider.getDateID(date)),
                        holiday?.description ?: ""
                    )
                )
            }

            val nextDate = calendar.clone() as Calendar
            beforeDate.apply { add(Calendar.MONTH, 1) }

            for (i in 1 ..  35 - days.size){
                val nextDate: Calendar = (nextDate.clone() as Calendar).apply {
                    set(Calendar.DATE, i)
                }
                val holiday: Holiday? = holidayRepository.getHoliday(dateProvider.getDateID(nextDate))
                days.add(
                    CalendarDate(
                        nextDate,
                        dateProvider.getDateID(nextDate),
                        if (nextDate[7] == 1 || nextDate[7] == 7 || holiday != null) DayType.WEEKEND else DayType.WEEKDAY,
                        jobDatabase.getEvent(dateProvider.getDateID(nextDate)),
                        holiday?.description ?: ""
                    )
                )
            }
            dayList.clear()
            dayList.addAll(days)
        }
    }

    override fun setNextMonth() {
        val newDate = currDate.value.clone() as Calendar
        newDate.apply {
            set(Calendar.DATE, 1)
            add(Calendar.MONTH, 1)
        }
        currDate.value = newDate
        setDayList()
    }

    override fun setBeforeMonth() {
        val newDate = currDate.value.clone() as Calendar
        newDate.apply {
            set(Calendar.DATE, 1)
            add(Calendar.MONTH, -1)
        }
        currDate.value = newDate
        setDayList()
    }

    override fun setSelectedDate(date: Calendar) {
        selectedDate.value = date
        todoDTOList.clear()
        dayList.find { it.date == dateProvider.getDateID(selectedDate.value)}
            ?.let { todoDTOList.addAll(it.todos) }
    }

    fun addTodoList(){
        val event = Event(
            dateProvider.getDateToString(selectedDate.value),
            Type.DAY,
            "",
            dateProvider.getDateToString(selectedDate.value),
            dateProvider.getDateToString(selectedDate.value),
            true,
            30L,
            dateProvider.getDateID(selectedDate.value)
        )
        viewModelScope.launch(Dispatchers.IO){
            jobDatabase.insertEvent(event)
            todoDTOList.clear()
            todoDTOList.addAll(jobDatabase.getEvent(dateProvider.getDateToString(selectedDate.value)))
        }
    }

    fun deleteTodo(todo: Event){
        viewModelScope.launch(Dispatchers.IO) {
            jobDatabase.deletedEvent(todo)
            todoDTOList.clear()
            todoDTOList.addAll(jobDatabase.getEvent(dateProvider.getDateToString(selectedDate.value)))
        }
    }

    override fun setDayList(){
        viewModelScope.launch(Dispatchers.IO) {
            val days = ArrayList<CalendarDate>()
            val beforeDate = currDate.value.clone() as Calendar
            beforeDate.apply {
                add(Calendar.MONTH, -1)
            }
            val beforeDaySize = currDate.value.get(Calendar.DAY_OF_WEEK) - 1
            val maxOfBeforeDate = beforeDate.getActualMaximum(Calendar.DAY_OF_MONTH)
            for (i in maxOfBeforeDate - beforeDaySize + 1  .. maxOfBeforeDate){
                val date = beforeDate.clone() as Calendar
                date.apply {
                    set(Calendar.DATE, i)
                }
                val holiday: Holiday? = holidayRepository.getHoliday(dateProvider.getDateID(date))
                days.add(
                    CalendarDate(
                    date,
                    dateProvider.getDateID(date),
                    if (date[7] == 1 || date[7] == 7 || holiday != null) DayType.WEEKEND else DayType.WEEKDAY,
                    jobDatabase.getEvent(dateProvider.getDateID(date)),
                    holiday?.description ?: ""
                )
                )
            }

            for (i in 1 .. currDate.value.getActualMaximum(Calendar.DAY_OF_MONTH)){
                val date = currDate.value.clone() as Calendar
                date.apply {
                    set(Calendar.DATE, i)
                }
                val holiday: Holiday? = holidayRepository.getHoliday(dateProvider.getDateID(date))
                days.add(
                    CalendarDate(
                    date,
                    dateProvider.getDateID(date),
                    if (date[7] == 1 || date[7] == 7 || holiday != null) DayType.WEEKEND else DayType.WEEKDAY,
                    jobDatabase.getEvent(dateProvider.getDateID(date)),
                    holiday?.description ?: ""
                )
                )
            }

            val nextDate = currDate.value.clone() as Calendar
            beforeDate.apply {
                add(Calendar.MONTH, 1)
            }

            for (i in 1 ..  35 - days.size){
                val date = nextDate.clone() as Calendar
                date.apply {
                    set(Calendar.DATE, i)
                }
                val holiday: Holiday? = holidayRepository.getHoliday(dateProvider.getDateID(date))
                days.add(
                    CalendarDate(
                    date,
                    dateProvider.getDateID(date),
                    if (date[7] == 1 || date[7] == 7 || holiday != null) DayType.WEEKEND else DayType.WEEKDAY,
                    jobDatabase.getEvent(dateProvider.getDateID(date)),
                    holiday?.description ?: ""
                )
                )
            }
            dayList.clear()
            dayList.addAll(days)
        }
    }
}