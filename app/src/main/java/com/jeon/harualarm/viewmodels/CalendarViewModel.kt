package com.jeon.harualarm.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CalendarViewModel(): ViewModel() {
    var currDate = mutableStateOf(Calendar.getInstance(Locale.KOREA))
        private set
    var selectedDate = mutableStateOf(Calendar.getInstance())
    var dayList: SnapshotStateList<Date> = mutableStateListOf()

    init {
        setDayList() // 초기 날짜 리스트 설정
    }

    fun setNextMonth() {
        val newDate = Calendar.getInstance().apply {
            time = currDate.value.time // 현재 선택된 날짜를 기반으로 새로운 달 계산
            add(Calendar.MONTH, 1)
        }
        currDate.value = newDate
        setDayList() // 날짜 리스트 업데이트
    }

    fun setBeforeMonth() {
        val newDate = Calendar.getInstance().apply {
            time = currDate.value.time // 현재 선택된 날짜를 기반으로 새로운 달 계산
            add(Calendar.MONTH, -1)
        }
        currDate.value = newDate
        setDayList() // 날짜 리스트 업데이트
    }

    fun setSelectedDate(selectedYear: Int, selectedMonth: Int, selectedDay: Int) {
        val newDate = Calendar.getInstance().apply {
            time = selectedDate.value.time // 현재 선택된 날짜를 기반으로 새로운 날짜 계산
            set(Calendar.YEAR, selectedYear)
            set(Calendar.MONTH, selectedMonth)
            set(Calendar.DAY_OF_MONTH, selectedDay) // 선택한 날짜로 변경
        }
        selectedDate.value = newDate
    }

    private fun setDayList() {
        dayList.clear() // 기존 리스트 초기화

        // 현재 월의 최대 일수 및 첫 번째 날의 요일 계산
        val monthDayMax = currDate.value.getActualMaximum(Calendar.DAY_OF_MONTH)
        val monthFirstDay = currDate.value.get(Calendar.DAY_OF_WEEK) // 요일 (1=일요일, 7=토요일)

        // 이전 월과 다음 월의 날짜 계산
        val previousMonth = currDate.value.clone() as Calendar
        previousMonth.add(Calendar.MONTH, -1)
        val previousMonthMaxDay = previousMonth.getActualMaximum(Calendar.DAY_OF_MONTH)

        val nextMonth = currDate.value.clone() as Calendar
        nextMonth.add(Calendar.MONTH, 1)

        // 날짜 리스트 생성
        // 이전 월 날짜 추가
        val daysFromPreviousMonth = monthFirstDay - 1 // 이전 월에서 가져올 날짜 수
        for (i in previousMonthMaxDay - daysFromPreviousMonth + 1..previousMonthMaxDay) {
            dayList.add(previousMonth.apply { set(Calendar.DAY_OF_MONTH, i) }.time)
        }

        // 현재 월 날짜 추가
        for (i in 1..monthDayMax) {
            dayList.add(currDate.value.apply { set(Calendar.DAY_OF_MONTH, i) }.time)
        }

        // 다음 월 날짜 추가
        val remainingDays = 35 - dayList.size // 총 35일로 맞추기
        for (i in 1..remainingDays) {
            dayList.add(nextMonth.apply { set(Calendar.DAY_OF_MONTH, i) }.time)
        }

        // 디버깅: 추가된 날짜 출력
        dayList.forEach { date ->
            val calendar = Calendar.getInstance().apply { time = date }
            println("${calendar.get(Calendar.YEAR)}-${calendar.get(Calendar.MONTH) + 1}-${calendar.get(Calendar.DAY_OF_MONTH)} - Day of Week: ${calendar.get(Calendar.DAY_OF_WEEK)}")
        }
    }


}