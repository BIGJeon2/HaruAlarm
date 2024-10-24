package com.jeon.harualarm.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.jeon.harualarm.api.model.VO.DayType
import com.jeon.harualarm.database.model.DTO.Event
import com.jeon.harualarm.model.DTO.CalendarDate
import com.jeon.harualarm.util.DateConverter
import com.jeon.harualarm.util.DateProvider
import java.util.Calendar

class FakeCalendarViewModel() : ViewModel(), CalendarViewModelInterface {
    override var dateConverter = DateConverter()
    override var dateProvider = DateProvider()
    override var currDate = mutableStateOf(Calendar.getInstance().apply {
        set(Calendar.DATE, 1)
    })
    override var selectedDate = mutableStateOf(Calendar.getInstance())
    override var dayList: SnapshotStateList<CalendarDate> = mutableStateListOf()
    override var todoList: SnapshotStateList<Event> = mutableStateListOf()

    init {
        for (i in 1 until 35){
            dayList.add(
                CalendarDate(
                    Calendar.getInstance(),
                    "121212",
                    if (i == 1 || i == 7) DayType.HOLIDAY else DayType.WEEKDAY,
                    listOf(),
                    "휴일입니다"
                )
            )
        }
    }

    override fun setNextMonth() {
        TODO("Not yet implemented")
    }

    override fun setBeforeMonth() {
        TODO("Not yet implemented")
    }

    override fun setSelectedDate(date: Calendar) {
        TODO("Not yet implemented")
    }

    override fun setDayList() {
        TODO("Not yet implemented")
    }

}