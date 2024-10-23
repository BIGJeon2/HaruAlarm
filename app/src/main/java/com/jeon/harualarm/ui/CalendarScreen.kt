package com.jeon.harualarm.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jeon.harualarm.api.model.DayType
import com.jeon.harualarm.ui.theme.HaruAlarmTheme
import com.jeon.harualarm.ui.theme.MainColor
import com.jeon.harualarm.util.DateProvider
import com.jeon.harualarm.viewmodels.CalendarViewModel
import com.jeon.harualarm.viewmodels.CalendarViewModelInterface
import com.jeon.harualarm.viewmodels.FakeCalendarViewModel
import java.util.Calendar
import kotlin.math.abs


class CalendarScreen() {
    @Composable
    fun CalendarView(viewmodel: CalendarViewModelInterface) {
        var startX by remember { mutableFloatStateOf(0f)}
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
                CalendarHeader(viewmodel)
                CalendarDayName()
                CalendarDayList(viewmodel)
            }
        }
    }

    @Composable
    private fun CalendarHeader(viewmodel: CalendarViewModelInterface) {
        // selectedDate를 State로 관찰
        val selectedDate = viewmodel.currDate.value
        val currentYear = Calendar.getInstance().get(Calendar.YEAR) // 현재 연도 가져오기

        // 이전 및 다음 달 계산
        val nextMonth = DateProvider().getNextMonth(selectedDate.time)
        val currMonth = DateProvider().getMonthToString(selectedDate.time)
        val selectedYear = Calendar.getInstance().apply { time = selectedDate.time }.get(Calendar.YEAR)

        // 현재 연도와 선택된 연도 비교
        val displayText = if (currentYear != selectedYear) {
            "${selectedYear}.${currMonth} " // 연도 포함
        } else {
            currMonth // 연도 제외
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = {
                    viewmodel.setBeforeMonth()
                }
            ){
                Text(
                    text = DateProvider().getBeforeMonth(selectedDate.time),
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontStyle = FontStyle.Normal,
                    textAlign = TextAlign.Start
                )
            }

            TextButton(
                onClick = {
                    //viewmodel.setBeforeMonth()
                }
            ){
                Text(
                    text = displayText,
                    fontSize = 24.sp,
                    fontStyle = FontStyle.Normal,
                    textAlign = TextAlign.Start
                )
            }

            TextButton(
                onClick = {
                    viewmodel.setNextMonth()
                }
            ){
                Text(
                    text = nextMonth,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontStyle = FontStyle.Normal,
                    textAlign = TextAlign.Start,
                )
            }
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
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }
    }

    @Composable
    private fun CalendarDayList(viewmodel: CalendarViewModelInterface) {
        // ViewModel에서 날짜 리스트 가져오기
        val days = viewmodel.dayList
        val today = Calendar.getInstance() // 오늘 날짜 가져오기
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
                            val backgroundColor = if (viewmodel.selectedDate.value == displayDay.calendarDate) MainColor else Color.White
                            if (displayDay.calendarDate.get(Calendar.DAY_OF_MONTH) == 1) {
                                isCurrentMonth = !isCurrentMonth
                            }

                            // 오늘 날짜와 비교하여 텍스트 색상 설정
                            val todayTextColor = if (displayDay.date == DateProvider().getDateToString(today)) {
                                Color.Cyan // 오늘 날짜의 텍스트 색상
                            } else {
                                if (days[index].type == DayType.WEEKDAY){
                                    // 기본 텍스트 색상
                                    Color.Black
                                }else{
                                    Color.Red
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(0.6f)
                                    .padding(2.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(backgroundColor),
                                contentAlignment = Alignment.Center
                            ) {
                                Column {
                                    TextButton(
                                        onClick = {
                                            viewmodel.setSelectedDate(displayDay.calendarDate)
                                        },
                                        shape = RoundedCornerShape(10.dp),
                                        modifier = Modifier
                                            .fillMaxSize(),
                                        contentPadding = PaddingValues(2.dp)
                                    ) {
                                        Column(
                                            Modifier.fillMaxSize(),
                                            verticalArrangement = Arrangement.SpaceAround,
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = displayDay.calendarDate.get(Calendar.DAY_OF_MONTH).toString(),
                                                color = todayTextColor,
                                                fontSize = 10.sp,
                                            )
                                            Text(
                                                text = "+2",
                                                color = Color.Blue,
                                                fontSize = 10.sp,
                                            )
                                            Text(
                                                text = displayDay.description,
                                                maxLines = 1,
                                                color = todayTextColor,
                                                fontSize = 10.sp,
                                            )
                                        }
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
        val vm = FakeCalendarViewModel()
        CalendarScreen().CalendarView(viewmodel = vm)
    }
}