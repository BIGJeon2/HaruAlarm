package com.jeon.harualarm.viewmodels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeon.harualarm.database.InternalDatabase
import com.jeon.harualarm.database.Type
import com.jeon.harualarm.database.data.Todo
import com.jeon.harualarm.database.repository.TodoRepository
import com.jeon.harualarm.util.DateProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

class CalendarViewModel(application: Application): ViewModel() {
    private val repository: TodoRepository
    var currDate = mutableStateOf(Calendar.getInstance())
        private set
    private var selectedDate = mutableStateOf(Calendar.getInstance())

    init {
        val database = InternalDatabase.getDatabase(application).todoDao()
        repository = TodoRepository(database)
    }

    fun setNextMonth() {
        val newDate = Calendar.getInstance().apply {
            time = currDate.value.time // 현재 선택된 날짜를 기반으로 새로운 달 계산
            add(Calendar.MONTH, 1)
        }
        currDate.value = newDate
    }

    fun setBeforeMonth() {
        val newDate = Calendar.getInstance().apply {
            time = currDate.value.time // 현재 선택된 날짜를 기반으로 새로운 달 계산
            add(Calendar.MONTH, -1)
        }
        currDate.value = newDate
    }

    fun setSelectedDate(selectedDay: Int){
        val newDate = Calendar.getInstance().apply {
            time = selectedDate.value.time // 현재 선택된 날짜를 기반으로 새로운 달 계산
            set(Calendar.DAY_OF_MONTH, selectedDay) // 선택한 날짜로 변경
        }
        selectedDate.value = newDate
    }

    fun addTodoList(time: Date){
        val newTodo = Todo().apply {
            type = Type.DAY
            name = time.toString()
            description = "Description"
            isAlarmEnabled = true
            creationDate = time
        }
        viewModelScope.launch(Dispatchers.IO){
            repository.insertToDoList(newTodo)
        }
    }

}