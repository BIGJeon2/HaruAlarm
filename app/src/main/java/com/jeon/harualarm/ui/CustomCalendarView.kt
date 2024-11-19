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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jeon.harualarm.util.DateConverter
import com.jeon.harualarm.util.DateProvider
import com.jeon.harualarm.CalendarDate
import com.jeon.harualarm.ui.theme.MainColor
import com.jeon.harualarm.viewmodels.CalendarViewModel
import com.jeon.model.vo.DayType
import java.util.ArrayList
import java.util.Calendar

val dateConverter = DateConverter()
val dateProvider = DateProvider()

class CustomCalendarView {

    @Composable
    fun BasicCalendarView(
        calendarViewModel: CalendarViewModel,
        paddingValues: Dp,
        isSwipe: Boolean,
        onLeftSwipe: () -> Unit?,
        onRightSwipe: () -> Unit?,
        onDateClick: (Calendar) -> Unit?
    ) {
        var startX by remember { mutableFloatStateOf(0f) }
        var endX by remember { mutableFloatStateOf(0f) }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .clip(RoundedCornerShape(15.dp))
                .background(Color.White)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragStart = { offset ->
                            startX = offset.x
                        },
                        onDragEnd = {
                            val dragAmount = startX - endX
                            if (kotlin.math.abs(dragAmount) >= 250 && isSwipe) {
                                // 드래그 종료 시 방향 판단
                                if (dragAmount < 0) onLeftSwipe()
                                else onRightSwipe()
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
                CalendarHeader(calendarViewModel.currDate.value, onLeftSwipe, onRightSwipe)
                HorizontalDivider(
                    thickness = 1.dp,
                    color = MainColor
                )
                CalendarDayName()
                CalendarDayList(calendarViewModel, calendarViewModel.dayList, onDateClick)
            }
        }
    }

    @Composable
    fun CalendarHeader(date: Calendar, onLeftSwipe: () -> Unit?, onRightSwipe: () -> Unit? ) {
        val selectedDate = date.clone() as Calendar
        val beforeMonth = dateConverter.getMonth(dateProvider.getBeforeMonth(selectedDate))
        val nextMonth = dateConverter.getMonth(dateProvider.getNextMonth(selectedDate))
        val currMonth = dateConverter.getMonth(selectedDate)
        val isCurrYear = dateConverter.getYear(selectedDate) == dateConverter.getYear(Calendar.getInstance())

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = {
                    onLeftSwipe()
                }
            ){
                Text(
                    text = "$beforeMonth",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontStyle = FontStyle.Normal,
                    textAlign = TextAlign.Start
                )
            }

            TextButton(
                onClick = {

                }
            ){
                Text(
                    text = if (isCurrYear) { currMonth.toString() } else { "${dateConverter.getYear(selectedDate)}.${currMonth}" },
                    fontSize = 24.sp,
                    fontStyle = FontStyle.Normal,
                    textAlign = TextAlign.Center
                )
            }

            TextButton(
                onClick = {
                    onRightSwipe()
                }
            ){
                Text(
                    text = "$nextMonth",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontStyle = FontStyle.Normal,
                    textAlign = TextAlign.Start,
                )
            }
        }
    }

    @Composable
    fun CalendarDayName(){
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
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }
    }

    @Composable
    fun CalendarDayList (
        calendarViewModel: CalendarViewModel?,
        dayList: List<CalendarDate>,
        onDateClick: (Calendar) -> Unit?
    ) {
        val dateConverter = DateConverter()
        val today = Calendar.getInstance() // 오늘 날짜 가져오기
        Column(
            modifier = Modifier
                .background(Color.Transparent)
                .padding(top = 6.dp)
        ) {
            repeat(5) { week ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    repeat(7) { day ->
                        val index = week * 7 + day
                        if (index < dayList.size) {
                            val displayDay = dayList[index]
                            val isToday = displayDay.dateID == dateConverter.dateID(today)
                            val backgroundColor = if (isToday) MainColor else Color.White
                            val todayTextColor =
                                if (dayList[index].type == DayType.WEEKDAY) Color.Black else Color.Red
                            val date = dateConverter.getDay(displayDay.calendarDate)
                            val description = displayDay.description
                            val todoList = calendarViewModel?.getTodoList(displayDay)
                                ?.collectAsState(
                                    initial = emptyList()
                                )
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(0.55f)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(backgroundColor),
                                contentAlignment = Alignment.TopCenter
                            ) {
                                Column {
                                    TextButton(
                                        onClick = {
                                            onDateClick(displayDay.calendarDate)
                                        },
                                        shape = RoundedCornerShape(10.dp),
                                        modifier = Modifier.fillMaxSize(),
                                        contentPadding = PaddingValues(0.dp)
                                    ) {
                                        Column(
                                            modifier = Modifier.fillMaxSize(),
                                            verticalArrangement = Arrangement.Top,
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                        ) {
                                            Text(
                                                text = "$date",
                                                color = todayTextColor,
                                                fontSize = 10.sp,
                                            )
                                            if (displayDay.type == DayType.HOLIDAY) {
                                                Text(
                                                    text = description,
                                                    maxLines = 1,
                                                    color = todayTextColor,
                                                    fontSize = 10.sp,
                                                )
                                            }
                                            if (todoList != null && todoList.value.isNotEmpty()){
                                                val todoItems = todoList.value
                                                if (todoItems.size >= 3){
                                                    if (displayDay.type == DayType.HOLIDAY){
                                                        Column {
                                                            val todo = todoItems[0]
                                                            AlarmItemView().AlarmItemMinimal(
                                                                todoEvent = todo,
                                                                padding = 0.dp
                                                            )
                                                        }
                                                    }else{
                                                        Column {
                                                            for (i in 0..1) {
                                                                val todo = todoItems[i]
                                                                AlarmItemView().AlarmItemMinimal(
                                                                    todoEvent = todo,
                                                                    padding = 0.dp
                                                                )
                                                            }
                                                        }
                                                    }
                                                    Text(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        text = "+${todoItems.size - 2}",
                                                        fontSize = 10.sp,
                                                        color = Color.Black,
                                                        textAlign = TextAlign.Center)
                                                }else{
                                                    Column {
                                                        for (todo in todoItems) {
                                                            AlarmItemView().AlarmItemMinimal(
                                                                todoEvent = todo,
                                                                padding = 0.dp
                                                            )
                                                        }
                                                    }
                                                }

                                            }
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

@Preview(showBackground = true)
@Composable
fun CalendarViewPreview(){
    val date = Calendar.getInstance()
    val dayList = ArrayList<CalendarDate>()
    for (i in 0..34){
        dayList.add(
            CalendarDate(
                date,
                dateConverter.dateID(date),
                DayType.WEEKDAY,
                "Description"
            )
        )
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(modifier = Modifier.padding(6.dp)) {
            CustomCalendarView().CalendarHeader(date = date, onLeftSwipe = { /*TODO*/ }) {

            }
            HorizontalDivider(
                thickness = 1.dp,
                color = MainColor
            )
            CustomCalendarView().CalendarDayName()
            CustomCalendarView().CalendarDayList(null, dayList, onDateClick = {})
        }
    }
}