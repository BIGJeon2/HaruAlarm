package com.jeon.harualarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jeon.model.vo.DayType
import com.jeon.harualarm.ui.theme.HaruAlarmTheme
import com.jeon.harualarm.ui.theme.MainColor
import com.jeon.harualarm.util.DateConverter
import com.jeon.harualarm.util.DateProvider
import com.jeon.harualarm.viewmodels.CalendarViewModel
import com.jeon.harualarm.viewmodels.CalendarViewModelInterface
import com.jeon.harualarm.viewmodels.FakeCalendarViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import kotlin.math.abs
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val calendarViewModel: CalendarViewModel by viewModels()
        enableEdgeToEdge()
        setContent {
            HaruAlarmTheme {
                Column(
                    modifier = Modifier.background(MainColor)
                ){
                    Spacer(modifier = Modifier.height(20.dp))
                    CalendarView(calendarViewModel)
                }
            }
        }
    }
}

@Composable
fun CalendarView(viewmodel: CalendarViewModelInterface) {
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
            CalendarHeader(viewmodel)
            HorizontalDivider(
                thickness = 2.dp,
                color = Color.LightGray
            )
            CalendarDayName()
            CalendarDayList(viewmodel)
        }
    }
}

@Composable
private fun CalendarHeader(viewmodel: CalendarViewModelInterface) {
    val dateProvider = DateProvider()
    val dateConverter = DateConverter()
    val selectedDate = viewmodel.currDate.value
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
                viewmodel.setBeforeMonth()
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
                viewmodel.setNextMonth()
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
    val dateConverter = DateConverter()
    val days = viewmodel.dayList
    val today = Calendar.getInstance() // 오늘 날짜 가져오기
    Column(
        modifier = Modifier
            .background(Color.Transparent)
            .padding(top = 12.dp)
    ) {
        repeat(5) { week ->
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                repeat(7) { day ->
                    val index = week * 7 + day
                    if (index < days.size) {
                        val displayDay = days[index]
                        val isToday = displayDay.dateID == dateConverter.dateID(today)
                        val backgroundColor = if (isToday) MainColor else Color.White
                        val todayTextColor = if (days[index].type == DayType.WEEKDAY) Color.Black  else Color.Red
                        val date = dateConverter.getDay(displayDay.calendarDate)
                        val description = displayDay.description
                        val todoText = if (displayDay.eventCount != 0) "+${displayDay.eventCount}" else ""
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(0.6f)
                                .padding(2.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(backgroundColor),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Column {
                                TextButton(
                                    onClick = {

                                    },
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier.fillMaxSize(),
                                    contentPadding = PaddingValues(2.dp)
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
                                        if (displayDay.type != com.jeon.model.vo.DayType.WEEKDAY){
                                            Text(
                                                text = description,
                                                maxLines = 1,
                                                color = todayTextColor,
                                                fontSize = 10.sp,
                                            )
                                        }
                                        Text(
                                            text = todoText,
                                            color = Color.Blue,
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HaruAlarmTheme {
        val vm = FakeCalendarViewModel()
        CalendarView(viewmodel = vm)
    }
}