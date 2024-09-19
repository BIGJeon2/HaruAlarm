package com.jeon.harualarm.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jeon.harualarm.util.DateProvider
import java.util.Calendar

class CalendarViewModel: ViewModel() {
    var currDate = mutableStateOf(Calendar.getInstance())
        private set
    var selectedDate = mutableStateOf(Calendar.getInstance())

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

}