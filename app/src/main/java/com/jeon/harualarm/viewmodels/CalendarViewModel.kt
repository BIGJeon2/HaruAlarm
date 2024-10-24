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
import java.util.Queue

class CalendarViewModel(private val holidayRepository: HolidayDAO, private val jobDatabase: EventDAO): ViewModel(), CalendarViewModelInterface {
    override var dateConverter = DateConverter()
    override var dateProvider = DateProvider()
    override var currDate = mutableStateOf(Calendar.getInstance().apply { set(Calendar.DATE, 1) })
    override var dayList: SnapshotStateList<CalendarDate> = mutableStateListOf()
    var todoList: SnapshotStateList<Event> = mutableStateListOf()

    init {
        setDayList()
    }

    override fun setDayList(){
        val calendar = currDate.value.clone() as Calendar
        val days = ArrayList<CalendarDate>()
        viewModelScope.launch(Dispatchers.IO) {
            //Add before date
            val beforeDate = dateProvider.getBeforeMonth(calendar)
            val beforeDaySize = calendar.get(Calendar.DAY_OF_WEEK) - 1
            val beforeDayList = dateProvider.getMonthDayList(beforeDate)
            for (idx in beforeDayList.size - beforeDaySize until beforeDayList.size){
                days.add(getCalendarDate(beforeDayList[idx]))
            }

            //Add curr date
            val currDayList = dateProvider.getMonthDayList(calendar)
            for (newDate in currDayList){
                days.add(getCalendarDate(newDate))
            }

            //Add next date
            val nextDate = dateProvider.getNextMonth(calendar)
            val nextDateList = dateProvider.getMonthDayList(nextDate)
            for (idx in 0 until  35 - days.size){
                days.add(getCalendarDate(nextDateList[idx]))
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

    private suspend fun getCalendarDate(date: Calendar): CalendarDate{
        val dateID = dateConverter.dateID(date)
        val holiday = getHoliday(dateID)
        val type = if (holiday != null) DayType.HOLIDAY else checkDayType(date)
        val eventSize = jobDatabase.getEventSize(dateID)
        val description = holiday?.description ?: ""
        return CalendarDate(date, dateID, type, eventSize, description)
    }

    private suspend fun getHoliday(dateID: String): Holiday?{
        return holidayRepository.getHoliday(dateID)
    }

    private fun checkDayType(date: Calendar): DayType{
        return if (date[7] == 1 || date[7] == 7) DayType.WEEKEND else DayType.WEEKDAY
    }

}