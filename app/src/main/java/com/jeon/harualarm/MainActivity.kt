package com.jeon.harualarm

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.ColorRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jeon.harualarm.data.Alarm
import com.jeon.harualarm.ui.theme.HaruAlarmTheme
import com.jeon.harualarm.ui.theme.MainColor
import com.jeon.harualarm.ui.theme.SecondaryColor
import com.jeon.harualarm.ui.theme.white
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale
import kotlin.math.abs

val alarms = mutableListOf(
    Alarm("07:00 AM", true, listOf("월", "화", "수", "목", "금")),
    Alarm("08:00 AM", false, listOf("토", "일")),
    Alarm("09:00 AM", true, listOf("월", "수", "금")),
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HaruAlarmTheme {
                MainView()
            }
        }
    }
}

@Composable
private fun MainView(){
    Column(
        modifier = Modifier.background(MainColor)
    ){
        Spacer(modifier = Modifier.height(20.dp))
        CalenderView()
        TodoListContainer()
    }
}

@Composable
private fun CalenderView() {
    val calenderInstance: Calendar = Calendar.getInstance()
    val time = remember { mutableStateOf(calenderInstance) }
    var startX by remember { mutableStateOf(0f) }
    var endX by remember { mutableStateOf(0f) }
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
                            if (dragAmount < 0) {
                                // Right swipe -> 이전 달로 이동
                                val newDate = Calendar.getInstance()
                                newDate.time = time.value.time
                                newDate.add(Calendar.MONTH, -1)
                                time.value = newDate
                            } else {
                                // Left swipe -> 다음 달로 이동
                                val newDate = Calendar.getInstance()
                                newDate.time = time.value.time
                                newDate.add(Calendar.MONTH, 1)
                                time.value = newDate
                            }
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
            CalendarHeader(time)
            CalendarDayName()
            CalendarDayList(time)
        }
    }
}

@Composable
private fun CalendarHeader(date: MutableState<Calendar>){
    // xxxx년 xx월
    val resultTime = SimpleDateFormat("yy.MM", Locale.KOREA).format(date.value.time)
    val beforeMonthDate = Calendar.getInstance()
    beforeMonthDate.time = date.value.time
    beforeMonthDate.add(Calendar.MONTH, -1)
    val nextMonthDate = Calendar.getInstance()
    nextMonthDate.time = date.value.time
    nextMonthDate.add(Calendar.MONTH, -1)
    val nextMonth = SimpleDateFormat("yy.MM", Locale.KOREA).format(nextMonthDate.time)
    val beforeMonth = SimpleDateFormat("yy.MM", Locale.KOREA).format(beforeMonthDate.time)

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
            text = resultTime,
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
private fun CalendarDayList(date: MutableState<Calendar>) {
    // 달력 그리는 공식
    date.value.set(Calendar.DAY_OF_MONTH, 1)

    val monthDayMax = date.value.getActualMaximum(Calendar.DAY_OF_MONTH)
    val monthFirstDay = date.value.get(Calendar.DAY_OF_WEEK) - 1

    // 이전 월과 다음 월의 날짜 계산
    val previousMonth = date.value.clone() as Calendar
    previousMonth.add(Calendar.MONTH, -1)
    val previousMonthMaxDay = previousMonth.getActualMaximum(Calendar.DAY_OF_MONTH)

    val nextMonth = date.value.clone() as Calendar
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
                        val backgroundColor = if (days[index] == 15) MainColor else Color.White
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

@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("AutoboxingStateCreation")
@Composable
fun TodoListContainer() {
    val selectedIndex by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(Color.White),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ){
            AnimatedContent(
                targetState = selectedIndex,
                transitionSpec = {
                    (slideInHorizontally { width -> width } + fadeIn()).togetherWith(
                        slideOutHorizontally { width -> -width } + fadeOut())
                }, label = ""
            ) { targetIndex ->
                when (targetIndex) {
                    0 -> AlarmCard(alarms)
                    1 -> CardBox(content = "Calendar Content")
                    2 -> CardBox(content = "Settings Content")
                }
            }
        }
    }
}

@Composable
fun CardBox(content: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = SecondaryColor),
        contentAlignment = Alignment.Center
    ) {
        Text(text = content, style = MaterialTheme.typography.headlineMedium, color = Color.Black)
        Text(text = content, style = MaterialTheme.typography.headlineMedium, color = Color.Black)
        Text(text = content, style = MaterialTheme.typography.headlineMedium, color = Color.Black)
    }
}

@SuppressLint("MutableCollectionMutableState")
@Composable
fun AlarmCard(alarms: MutableList<Alarm>) {
    val alarmList by remember {
        mutableStateOf(alarms)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
        ){
            Text(
                text = "일정",
                style = MaterialTheme.typography.titleSmall,
                color = Color.Black,
            )
            FloatingActionButton(
                onClick = { addAlarm() },
                modifier = Modifier
                    .size(40.dp, 40.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add alarm button")
            }
        }

        LazyColumn {
            items(alarmList) { alarm ->
                AlarmItem(
                    time = alarm.time,
                    isEnabled = alarm.isEnabled,
                    daysOfWeek = alarm.daysOfWeek
                )
                HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
            }
        }
    }
}

fun addAlarm() {
    alarms.add(Alarm("07:00 AM", true, listOf("월", "화", "수", "목", "금")))
}

@Composable
fun AlarmItem(time: String, isEnabled: Boolean, daysOfWeek: List<String>) {
    var alarmEnabled by remember { mutableStateOf(isEnabled) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .padding(6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = time,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
            Switch(
                checked = alarmEnabled,
                onCheckedChange = { alarmEnabled = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color(0xFFA4E2A6),
                    uncheckedThumbColor = Color(0xFF625b71)
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HaruAlarmTheme {
        MainView()
    }
}