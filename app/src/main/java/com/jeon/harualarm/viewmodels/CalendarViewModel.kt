package com.jeon.harualarm.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeon.harualarm.api.model.DayType
import com.jeon.harualarm.database.model.DAO.HolidayDAO
import com.jeon.harualarm.database.model.DTO.CalendarDate
import com.jeon.harualarm.database.model.DTO.Holiday
import com.jeon.harualarm.util.DateProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class CalendarViewModel(private val holidayRepository: HolidayDAO): ViewModel() {
    private val dateProvider = DateProvider()
    var currDate = mutableStateOf(Calendar.getInstance().apply {
        set(Calendar.DATE, 1)
    })
        private set
    var selectedDate = mutableStateOf(Calendar.getInstance())
    var dayList: SnapshotStateList<CalendarDate> = mutableStateListOf()
    private lateinit var holidays: List<Holiday>

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getHoliday()
            setDayList()
        }
    }

    fun setNextMonth() {
        val newDate = currDate.value.clone() as Calendar
        newDate.apply {
            set(Calendar.DATE, 1)
            add(Calendar.MONTH, 1)
        }
        currDate.value = newDate
        setDayList()
    }

    fun setBeforeMonth() {
        val newDate = currDate.value.clone() as Calendar
        newDate.apply {
            set(Calendar.DATE, 1)
            add(Calendar.MONTH, -1)
        }
        currDate.value = newDate
        setDayList()
    }

    fun setSelectedDate(date: Calendar) {
        selectedDate.value = date
    }

    private fun setDayList(){
        dayList.clear()
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
            val holiday = holidays.find { it.date == dateProvider.getDateToString(date) }
            days.add(CalendarDate(
                    date,
                    dateProvider.getDateToString(date),
                    if (date[7] == 1 || date[7] == 7 || holiday != null) DayType.WEEKEND else DayType.WEEKDAY,
                holiday?.description ?: ""
            ))
        }

        for (i in 1 until currDate.value.get(Calendar.DAY_OF_MONTH)){
            val date = currDate.value.clone() as Calendar
            date.apply {
                set(Calendar.DATE, i)
            }
            val holiday = holidays.find { it.date == dateProvider.getDateToString(date) }
            days.add(CalendarDate(
                    date,
                    dateProvider.getDateToString(date),
                    if (date[7] == 1 || date[7] == 7 || holiday != null) DayType.WEEKEND else DayType.WEEKDAY,
                holiday?.description ?: ""
            ))
        }

        val nextDate = currDate.value.clone() as Calendar
        beforeDate.apply {
            add(Calendar.MONTH, 1)
        }

        for (i in 1 .. 35 - dayList.size){
            val date = nextDate.clone() as Calendar
            date.apply {
                set(Calendar.DATE, i)
            }
            val holiday = holidays.find { it.date == dateProvider.getDateToString(date) }
            days.add(CalendarDate(
                date,
                dateProvider.getDateToString(date),
                if (date[7] == 1 || date[7] == 7 || holiday != null) DayType.WEEKEND else DayType.WEEKDAY,
                holiday?.description ?: ""
            ))
        }
        dayList.addAll(days)
    }

    private suspend fun getHoliday() {
        holidays = holidayRepository.getAllHolidays()
    }
}