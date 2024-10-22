package com.jeon.harualarm.viewmodels

import android.annotation.SuppressLint
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeon.harualarm.api.client.ApiServiceFactory
import com.jeon.harualarm.api.model.DayType
import com.jeon.harualarm.database.model.DTO.CalendarDate
import com.jeon.harualarm.util.DateProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class CalendarViewModel(): ViewModel() {
    private val dateProvider = DateProvider()
    var currDate = mutableStateOf(Calendar.getInstance())
        private set
    var selectedDate = mutableStateOf(Calendar.getInstance())
    var previousMonthDays = SnapshotStateList<CalendarDate>()
    var dayList: SnapshotStateList<CalendarDate> = mutableStateListOf()
    var nextMonthDays = SnapshotStateList<CalendarDate>()

    init {
        loadMonthDate()
        setDayList()
    }

    private fun loadMonthDate(){
        val previousDate = Calendar.getInstance().apply {
            time = currDate.value.time // 현재 선택된 날짜를 기반으로 새로운 달 계산
            add(Calendar.MONTH, 1)
            set(Calendar.DATE, 1)
        }

        val nextDate = Calendar.getInstance().apply {
            time = currDate.value.time // 현재 선택된 날짜를 기반으로 새로운 달 계산
            add(Calendar.MONTH, -1)
            set(Calendar.DATE, 1)
        }

        previousMonthDays.addAll(dateProvider.getDaysList(previousDate))
        dayList.addAll(dateProvider.getDaysList(nextDate))
        nextMonthDays.addAll(dateProvider.getDaysList(currDate.value))
    }

    fun setNextMonth() {
        val newDate = Calendar.getInstance().apply {
            time = currDate.value.time // 현재 선택된 날짜를 기반으로 새로운 달 계산
            add(Calendar.MONTH, 1)
            set(Calendar.DATE, 1)
        }
        currDate.value = newDate
        setDayList() // 날짜 리스트 업데이트
    }

    fun setBeforeMonth() {
        val newDate = Calendar.getInstance().apply {
            time = currDate.value.time // 현재 선택된 날짜를 기반으로 새로운 달 계산
            add(Calendar.MONTH, -1)
            set(Calendar.DATE, 1)
        }
        currDate.value = newDate
        setDayList() // 날짜 리스트 업데이트
    }

    fun setSelectedDate(date: Calendar) {
        selectedDate.value = date
    }

    private fun setDayList() {
        dayList.clear()
        setHolidays(dateProvider.getDaysList(currDate.value))
    }

    @SuppressLint("DefaultLocale")
    private fun setHolidays(days: List<CalendarDate>) {
        val year = currDate.value.get(Calendar.YEAR)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val client = ApiServiceFactory.holidayAPI
                val response = client.getHolidays(year).execute()
                if (response.isSuccessful) {
                    response.body()?.response?.body?.items?.item?.forEach { holiday ->
                        days.find { it.date == holiday.locdate }?.let { day ->
                            day.type = DayType.HOLIDAY
                            day.description = holiday.dateName
                        }
                    }
                    withContext(Dispatchers.Main){
                        dayList.clear()
                        dayList.addAll(days)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                dayList.addAll(days)
            }
        }
    }
}