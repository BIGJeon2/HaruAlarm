package com.jeon.harualarm.ui

import android.widget.CalendarView
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jeon.harualarm.ui.theme.HaruAlarmTheme
import com.jeon.harualarm.ui.theme.MainColor
import com.jeon.harualarm.util.DateProvider
import com.jeon.harualarm.viewmodels.CalendarViewModel
import java.util.Calendar
import kotlin.math.abs

class CalendarScreen(private val viewmodel: CalendarViewModel) {
    @Composable
    fun CalendarView() {
        var startX by remember { mutableFloatStateOf(0f) }
        var endX by remember { mutableFloatStateOf(0f) }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color.White)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragStart = { offset ->
                            startX = offset.x
                        },
                        onDragEnd = {
                            val dragAmount = startX - endX
                            if (abs(dragAmount) >= 250) {
                                // 드래그 종료 시 방향 판단
                                if (dragAmount < 0) viewmodel.setBeforeMonth()
                                else viewmodel.setNextMonth()
                            }
                        },
                        onHorizontalDrag = { change, _ ->
                            change.consume()
                            endX = change.position.x
                        }
                    )
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(modifier = Modifier.padding(6.dp)) {
                CalendarHeader()
                CalendarDayName()
                CalendarDayList()
            }
        }
    }

    @Composable
    private fun CalendarHeader(){
        // selectedDate를 State로 관찰
        val selectedDate = viewmodel.selectedDate.value

        // 이전 및 다음 달 계산
        val nextMonth = DateProvider().getNextMonth(selectedDate)
        val currMonth =  DateProvider().getMonthToString(selectedDate)
        val beforeMonth = DateProvider().getBeforeMonth(selectedDate)

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = beforeMonth,
                fontSize = 12.sp,
                color = Color.Gray,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Start
            )
            Text(
                text = currMonth,
                fontSize = 24.sp,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Start
            )
            Text(
                text = nextMonth,
                fontSize = 12.sp,
                color = Color.Gray,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Start
            )
        }
    }

    @Composable
    private fun CalendarDayName(){
        val nameList = listOf("일","월","화","수","목","금","토")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            nameList.forEach {
                Box(
                    modifier = Modifier
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = it,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }
    }

    @Composable
    private fun CalendarDayList() {
        val selectedDate = viewmodel.selectedDate.value
        val monthDayMax = selectedDate.getActualMaximum(Calendar.DAY_OF_MONTH)
        val monthFirstDay = selectedDate.get(Calendar.DAY_OF_WEEK) - 1

        // 이전 월과 다음 월의 날짜 계산
        val previousMonth = selectedDate.clone() as Calendar
        previousMonth.add(Calendar.MONTH, -1)
        val previousMonthMaxDay = previousMonth.getActualMaximum(Calendar.DAY_OF_MONTH)

        val nextMonth = selectedDate.clone() as Calendar
        nextMonth.add(Calendar.MONTH, 1)
        val nextMonthMaxDay = nextMonth.getActualMaximum(Calendar.DAY_OF_MONTH)

        // 날짜 리스트 생성
        val days = mutableListOf<Int>()
        // 이전 월 날짜 추가
        for (i in (previousMonthMaxDay - monthFirstDay + 1)..previousMonthMaxDay) {
            days.add(i)
        }
        // 현재 월 날짜 추가
        for (i in 1..monthDayMax) {
            days.add(i)
        }
        // 다음 월 날짜 추가
        val remainingDays = 35 - days.size// 35는 5주 * 7일
        for (i in 1..remainingDays) {
            days.add(i)
        }

        Column(
            modifier = Modifier
                .background(Color.Transparent)
                .padding(top = 12.dp)
        ) {
            // 5주로 최대 설정
            var isCurrentMonth = false
            repeat(5) { week ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    repeat(7) { day ->
                        val index = week * 7 + day
                        if (index < days.size) {
                            val displayDay = days[index]
                            if (days[index] == 1){
                                isCurrentMonth = !isCurrentMonth
                            }
                            val textColor = if (isCurrentMonth) Color.Black else Color.LightGray
                            val backgroundColor = if (days[index].toString() == DateProvider().getDayToString(selectedDate)) MainColor else Color.White
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .padding(2.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(backgroundColor),
                                contentAlignment = Alignment.Center
                            ) {
                                Column{
                                    TextButton(
                                        onClick = {
                                            viewmodel.setSelectedDate(displayDay)
                                        },
                                    ){
                                        Text(
                                            text = displayDay.toString(),
                                            color = textColor,
                                            fontSize = 12.sp,
                                        )
                                    }
                                }

                            }
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}
@Preview(showSystemUi = true)
@Composable
fun CalendarPreview(){
    HaruAlarmTheme {
        val calendarViewModel = CalendarViewModel()
        CalendarScreen(calendarViewModel).CalendarView()
    }
}