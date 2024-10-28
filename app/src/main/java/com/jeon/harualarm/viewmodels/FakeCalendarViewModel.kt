package com.jeon.harualarm.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.jeon.database.Entity.TodoEvent
import com.jeon.harualarm.util.DateConverter
import com.jeon.harualarm.util.DateProvider
import com.jeon.model.vo.EventType
import java.util.Calendar

class FakeCalendarViewModel() : ViewModel(), CalendarViewModelInterface {
    override var dateConverter = DateConverter()
    override var dateProvider = DateProvider()
    override var currDate = mutableStateOf(Calendar.getInstance().apply {
        set(Calendar.DATE, 1)
    })
    override var dayList: SnapshotStateList<com.jeon.model.dto.CalendarDate> = mutableStateListOf()
    var nextTodo = TodoEvent(
        "소현이 약속",
        EventType.DAY,
        "설명 칸",
        dateConverter.dateToString(currDate.value),
        dateConverter.dateToString(currDate.value),
        true,
        30L,
        dateConverter.dateID(currDate.value)
    )


    init {
        for (i in 1 until 35){
            dayList.add(
                com.jeon.model.dto.CalendarDate(
                    Calendar.getInstance(),
                    "121212",
                    if (i == 1 || i == 7) com.jeon.model.vo.DayType.HOLIDAY else com.jeon.model.vo.DayType.WEEKDAY,
                    2,
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

    override fun setDayList() {
        TODO("Not yet implemented")
    }

}