package com.jeon.harualarm

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jeon.harualarm.data.Alarm
import com.jeon.harualarm.ui.theme.HaruAlarmTheme
import com.jeon.harualarm.ui.theme.MainColor
import com.jeon.harualarm.ui.theme.SecondaryColor
import com.jeon.harualarm.ui.theme.white

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
                MyApp()
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("AutoboxingStateCreation")
@Composable
fun MyApp() {
    var selectedIndex by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MainColor),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TopBar()
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
                    slideInHorizontally { width -> width } + fadeIn() with
                            slideOutHorizontally { width -> -width } + fadeOut()
                }, label = ""
            ) { targetIndex ->
                when (targetIndex) {
                    0 -> AlarmCard(alarms)
                    1 -> CardBox(content = "Calendar Content")
                    2 -> CardBox(content = "Settings Content")
                }
            }
        }
        BottomNavBar(selectedIndex) { selectedIndex = it }
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

@Composable
fun TopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(text = "하루의 시작", style = MaterialTheme.typography.titleLarge, color = Color.White)
    }
}

@SuppressLint("AutoboxingStateCreation")
@Composable
fun BottomNavBar(selectedIndex: Int, onItemSelected: (Int) -> Unit) {
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth(),
        containerColor = MainColor
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_alarm_24),
                    contentDescription = stringResource(R.string.alarm)
                )
            },
            selected = selectedIndex == 0,
            onClick = { onItemSelected(0) }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_calendar_month_24),
                    contentDescription = stringResource(R.string.calendar)
                )
            },
            selected = selectedIndex == 1,
            onClick = { onItemSelected(1) }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_settings_24),
                    contentDescription = stringResource(R.string.settings)
                )
            },
            selected = selectedIndex == 2,
            onClick = { onItemSelected(2) }
        )
    }
}

@Composable
fun AlarmCard(alarms: MutableList<Alarm>) {
    val alarmList by remember {
        mutableStateOf(alarms)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = white)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
        ){
            Text(
                text = "알람 설정",
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
                Divider(color = Color.LightGray, thickness = 1.dp)
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
            .padding(vertical = 8.dp)
            .padding(8.dp)
            .background(color = white)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
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
        DayOfWeekList(daysOfWeek = daysOfWeek)
    }
}

@Composable
fun DayOfWeekList(daysOfWeek: List<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val allDays = listOf("월", "화", "수", "목", "금", "토", "일")
        for (day in allDays) {
            val isSelected = day in daysOfWeek
            Text(
                text = day,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Thin,
                color = if (isSelected) Color.Black else Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HaruAlarmTheme {
        MyApp()
    }
}