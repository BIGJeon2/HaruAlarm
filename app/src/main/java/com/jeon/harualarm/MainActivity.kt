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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.jeon.harualarm.api.client.ApiServiceFactory
import com.jeon.harualarm.database.CalendarDatabase
import com.jeon.harualarm.database.model.DTO.Holiday
import com.jeon.harualarm.ui.CalendarPreview
import com.jeon.harualarm.ui.CalendarScreen
import com.jeon.harualarm.ui.theme.HaruAlarmTheme
import com.jeon.harualarm.ui.theme.MainColor
import com.jeon.harualarm.ui.theme.SecondaryColor
import com.jeon.harualarm.util.DateProvider
import com.jeon.harualarm.viewmodels.CalendarViewModel
import com.jeon.harualarm.viewmodels.JobsScreenViewModel
import com.jeon.harualarm.viewmodels.MainViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val calendarViewModel = ViewModelProvider(this, MainViewModelFactory(application))[CalendarViewModel::class.java]
        val jobsViewModel = ViewModelProvider(this, MainViewModelFactory(application))[JobsScreenViewModel::class.java]
        val holidayDatabase = CalendarDatabase.getDatabase(applicationContext).holidayDao()
        CoroutineScope(Dispatchers.IO).launch {
            if (holidayDatabase.getAllHolidaysCount() == 0){
                for (year in 2003 .. 2026){
                    try {
                        val client = ApiServiceFactory.holidayAPI
                        val response = client.getHolidays(year).execute()
                        if (response.isSuccessful) {
                            val items =  response.body()?.response?.body?.items?.item
                            val holidays = ArrayList<Holiday>()
                            if (items != null) {
                                for (i in items){
                                    if (i.isHoliday == "Y"){
                                        holidays.add(Holiday(year, i.locdate, i.dateName))
                                    }
                                }
                            }
                            holidayDatabase.insertAllHolidays(holidays)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
        enableEdgeToEdge()
        setContent {
            HaruAlarmTheme {
                Column(
                    modifier = Modifier.background(MainColor)
                ){
                    Spacer(modifier = Modifier.height(20.dp))
                    CalendarScreen().CalendarView(holidayDatabase, calendarViewModel)
                    TodoListContainer(jobsViewModel)
                }
            }
        }
    }
}

@SuppressLint("AutoboxingStateCreation")
@Composable
fun TodoListContainer(viewModel: JobsScreenViewModel) {
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
                    0 -> AlarmCard(viewModel)
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
fun AlarmCard(viewModel: JobsScreenViewModel) {
    val alarmList = viewModel.todoDTOList
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
                onClick = { viewModel.addTodoList(Calendar.getInstance().time)},
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
                    time = DateProvider().getFullDateToString(alarm.endTime),
                    isEnabled = alarm.isAlarm,
                    daysOfWeek = listOf("월", "화", "수", "목", "금")
                )
            }
        }
    }
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
        CalendarPreview()
    }
}