package com.jeon.harualarm.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeon.harualarm.api.client.ApiServiceFactory
import com.jeon.harualarm.api.model.DayType
import com.jeon.harualarm.api.model.Holidays
import com.jeon.harualarm.database.CalendarDatabase
import com.jeon.harualarm.database.model.DTO.CalendarDate
import com.jeon.harualarm.database.model.DTO.Holiday
import com.jeon.harualarm.util.DateProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class CalendarViewModel(private val calendarDB: CalendarDatabase) : ViewModel() {
    private val dateProvider = DateProvider()
    val selectDate = mutableStateOf(Calendar.getInstance())
    val currCalendar = mutableStateOf(Calendar.getInstance())
    val currDayList = mutableStateListOf<CalendarDate>()

    init {
        //오늘을 기준으로 데이터 초기화
        getHolidays(currCalendar.value.get(Calendar.YEAR))
        updateDayList()
    }

    //바뀐 달에 대한 달력 최신화
    private fun updateDayList(){
        currDayList.clear()
        currDayList.addAll(dateProvider.getDaysList(currCalendar.value))
    }

    //다을 달로 변경
    fun nextMonth(){
        val newDate = Calendar.getInstance().apply {
            time = currCalendar.value.time
            add(Calendar.MONTH, 1)
        }
        currCalendar.value = newDate
        if(newDate.get(Calendar.MONTH) == 1) getHolidays(newDate.get(Calendar.YEAR) + 1)
        //캘린더 days값 최신화
        updateDayList()
    }


    //이전 달로 변경
    fun beforeMonth(){
        val newDate = Calendar.getInstance().apply {
            time = currCalendar.value.time
            add(Calendar.MONTH, -1)
        }
        currCalendar.value = newDate
        if(newDate.get(Calendar.MONTH) == 1) getHolidays(newDate.get(Calendar.YEAR) - 1)
        //캘린더 days값 최신화
        updateDayList()
    }

    //연도가 바뀐 경우 Data 유무를 확인 하고 없을 경우 데이터를 Room DB에 저장한다.
    private fun getHolidays(year: Int){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val client = ApiServiceFactory.holidayAPI
                val response = client.getHolidays(year).execute()
                if (response.isSuccessful) {
                    val items =  response.body()?.response?.body?.items?.item
                    val holidays = ArrayList<Holiday>()
                    if (items != null) {
                        for (i in items){
                            holidays.add(Holiday(year, i.locdate, i.dateName))
                        }
                    }
                    calendarDB.holidayDao().insertAllHolidays(holidays)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}