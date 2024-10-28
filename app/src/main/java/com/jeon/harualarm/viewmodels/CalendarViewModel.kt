package com.jeon.harualarm.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeon.database.Entity.TodoEvent
import com.jeon.database.Entity.Holiday
import com.jeon.database.repository.HolidayRepository
import com.jeon.database.repository.TodoEventRepository
import com.jeon.harualarm.util.DateConverter
import com.jeon.harualarm.util.DateProvider
import com.jeon.model.vo.EventType
import com.jeon.rest_api.repository.HolidayApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val holidayRepository: HolidayRepository,
    private val jobDatabase: TodoEventRepository,
    private val holidayAPI: HolidayApiRepository
): ViewModel(), CalendarViewModelInterface {
    override var dateConverter = DateConverter()
    override var dateProvider = DateProvider()
    override var currDate = mutableStateOf(Calendar.getInstance().apply { set(Calendar.DATE, 1) })
    override var dayList: SnapshotStateList<com.jeon.model.dto.CalendarDate> = mutableStateListOf()
    var todoList: SnapshotStateList<TodoEvent> = mutableStateListOf()

    init {
        todoList.add(TodoEvent(
            "소현이 약속",
            EventType.DAY,
            "설명 칸",
            dateConverter.dateToString(currDate.value),
            dateConverter.dateToString(currDate.value),
            true,
            30L,
            dateConverter.dateID(currDate.value)
        ))
        viewModelScope.launch(Dispatchers.IO) {
            if (holidayRepository.getAllHolidaysCount() == 0){
                for (year in 2003 .. 2026){
                    try {
                        val response = holidayAPI.getAllHoliday(year).execute()
                        if (response.isSuccessful) {
                            val items =  response.body()?.response?.body?.items?.item
                            val holidays = ArrayList<Holiday>()
                            if (items != null) {
                                for (i in items){
                                    if (i.isHoliday == "Y"){
                                        holidays.add(Holiday(year, i.locdate, i.dateName))
                                    }
                                }
                                setDayList()
                            }
                            holidayRepository.insertAllHolidays(holidays)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
        setDayList()
    }

    override fun setDayList(){
        val calendar = currDate.value.clone() as Calendar
        val days = ArrayList<com.jeon.model.dto.CalendarDate>()
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

    private suspend fun getCalendarDate(date: Calendar): com.jeon.model.dto.CalendarDate {
        val dateID = dateConverter.dateID(date)
        val holiday = getHoliday(dateID)
        val type = if (holiday != null) com.jeon.model.vo.DayType.HOLIDAY else checkDayType(date)
        val eventSize = jobDatabase.getEventSize(dateID)
        val description = holiday?.description ?: ""
        return com.jeon.model.dto.CalendarDate(date, dateID, type, eventSize, description)
    }

    private suspend fun getHoliday(dateID: String): Holiday?{
        return holidayRepository.getHoliday(dateID)
    }

    private fun checkDayType(date: Calendar): com.jeon.model.vo.DayType {
        return if (date[7] == 1 || date[7] == 7) com.jeon.model.vo.DayType.WEEKEND else com.jeon.model.vo.DayType.WEEKDAY
    }

}