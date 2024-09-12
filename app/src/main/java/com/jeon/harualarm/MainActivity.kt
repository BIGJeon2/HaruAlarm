package com.jeon.harualarm

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.ViewModelProvider
import com.jeon.harualarm.data.Alarm
import com.jeon.harualarm.ui.CalendarScreen
import com.jeon.harualarm.ui.theme.HaruAlarmTheme
import com.jeon.harualarm.ui.theme.MainColor
import com.jeon.harualarm.ui.theme.SecondaryColor
import com.jeon.harualarm.util.DateProvider
import com.jeon.harualarm.viewmodels.CalendarViewModel
import java.util.Calendar
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
                val calendarViewModel = ViewModelProvider(this, MainViewModelFactory())[CalendarViewModel::class.java]
                Column(
                    modifier = Modifier.background(MainColor)
                ){
                    Spacer(modifier = Modifier.height(20.dp))
                    CalendarScreen(calendarViewModel).CalendarView()
                    TodoListContainer()
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

        LazyColumn(
            modifier = Modifier.padding(top = 12.dp)
        ) {
            items(alarmList) { alarm ->
                AlarmItem(
                    time = alarm.time,
                    isEnabled = alarm.isEnabled,
                    daysOfWeek = alarm.daysOfWeek
                )
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
            .padding(top = 12.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(MainColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "일과 목록 1",
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
            Text(
                text = time,
                fontSize = 12.sp,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
            /*Switch(
                checked = alarmEnabled,
                onCheckedChange = { alarmEnabled = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color(0xFFA4E2A6),
                    uncheckedThumbColor = Color(0xFF625b71)
                )
            )*/
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HaruAlarmTheme {
        val calendarViewModel = CalendarViewModel()

    }
}