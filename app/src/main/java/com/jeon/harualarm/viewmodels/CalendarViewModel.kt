package com.jeon.harualarm.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.jeon.harualarm.data.Todo
import java.util.Calendar
import java.util.Date

class CalendarViewModel: ViewModel() {
    val selectedDate = mutableListOf<Todo>()
    val time = mutableStateOf(Calendar.getInstance())

    fun nextMonth(){
        val newDate = Calendar.getInstance()
        newDate.time = time.value.time
        newDate.add(Calendar.MONTH, 1)
        time.value = newDate
    }

    fun setBeforeMonth(){
        val newDate = Calendar.getInstance()
        newDate.time = time.value.time
        newDate.add(Calendar.MONTH, -1)
        time.value = newDate
    }
    private fun getTodoList(date: Date){

    }
   /* val selectedDate = MutableLiveData<Date>*/
}