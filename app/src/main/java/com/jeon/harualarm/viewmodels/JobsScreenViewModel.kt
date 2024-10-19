package com.jeon.harualarm.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeon.harualarm.api.model.DayType
import com.jeon.harualarm.database.CalendarDatabase
import com.jeon.harualarm.database.model.DAO.CalendarDao
import com.jeon.harualarm.database.model.DTO.CalenderDate
import com.jeon.harualarm.database.model.DTO.TodoEvent
import com.jeon.harualarm.database.model.VO.Type
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

class JobsScreenViewModel(application: Application): ViewModel() {
    private val database: CalendarDao = CalendarDatabase.getDatabase(application).calendarDao()
    var selectedDate = mutableStateOf(CalenderDate(
        Calendar.getInstance().time,
        DayType.HOLIDAY
        )
    )
    var todoDTOList: SnapshotStateList<TodoEvent> = mutableStateListOf()
        private set

    init {
        getAllTodoList()
    }

    fun setSelectedDate(newDate: CalenderDate) {
        selectedDate.value = newDate
    }

    fun addTodoList(time: Date){
        val newTodo = TodoEvent(
            "first",
            Type.PERIOD,
            "NONE",
            selectedDate.value.date,
            selectedDate.value.date,
            false,
            30L,
            selectedDate.value.calendarId

        )
        viewModelScope.launch(Dispatchers.IO){
            database.insertEvent(newTodo)
            getAllTodoList()
        }
    }

    private fun getAllTodoList(){
        viewModelScope.launch(Dispatchers.IO) {
            val todos = database.getEventsForDate(selectedDate.value.calendarId)
            // 기존 todoList를 초기화하고 새로운 데이터를 추가합니다.
            todoDTOList.clear()
            todoDTOList.addAll(todos)
        }
    }

    fun deleteTodo(todo: TodoEvent){
        viewModelScope.launch(Dispatchers.IO) {
            database.deletedEvent(todo)
        }
    }


}