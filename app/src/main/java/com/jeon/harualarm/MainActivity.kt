package com.jeon.harualarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jeon.database.Entity.TodoEvent
import com.jeon.harualarm.ui.AlarmItemView
import com.jeon.harualarm.ui.CalendarViewPreview
import com.jeon.harualarm.ui.CustomCalendarView
import com.jeon.model.vo.DayType
import com.jeon.harualarm.ui.theme.HaruAlarmTheme
import com.jeon.harualarm.ui.theme.MainColor
import com.jeon.harualarm.util.DateConverter
import com.jeon.harualarm.util.DateProvider
import com.jeon.harualarm.viewmodels.CalendarViewModel
import com.jeon.model.vo.EventType
import dagger.hilt.android.AndroidEntryPoint
import de.drick.compose.edgetoedgepreviewlib.CameraCutoutMode
import de.drick.compose.edgetoedgepreviewlib.EdgeToEdgeTemplate
import de.drick.compose.edgetoedgepreviewlib.NavigationMode
import java.util.ArrayList
import java.util.Calendar

private val dateConverter = DateConverter()

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val calendarViewModel: CalendarViewModel by viewModels()
        enableEdgeToEdge()
        setContent {
            HaruAlarmTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MainColor)
                            .padding(innerPadding)
                    ){
                        AlarmItemView().AlarmItemExpand(
                            onCompleteClick = { event ->
                                calendarViewModel.addJob(event)
                            },
                            todoEvent = TodoEvent(
                                "소현이 약속",
                                EventType.DAY,
                                "설명 칸",
                                dateConverter.dateToString(calendarViewModel.currDate.value),
                                dateConverter.dateToString(calendarViewModel.currDate.value),
                                true,
                                30L,
                                dateConverter.dateID(calendarViewModel.currDate.value),
                                false
                            ),
                            padding = 6.dp
                        )
                        CustomCalendarView().BasicCalendarView(
                            calendarViewModel.currDate.value,
                            calendarViewModel.dayList,
                            paddingValues = 6.dp,
                            isSwipe = true,
                            onLeftSwipe = {calendarViewModel.setBeforeMonth()},
                            onRightSwipe = {calendarViewModel.setNextMonth()},
                            onDateClick = {/*TODO*/}
                        )
                        Text(text = "광고", modifier = Modifier.fillMaxWidth(), fontSize = 18.sp)
                    }
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    val currDate = Calendar.getInstance()
    val dayList = ArrayList<CalendarDate>()
    for (i in 0..34){
        dayList.add(
            CalendarDate(
                currDate,
                dateConverter.dateID(currDate),
                DayType.WEEKDAY,
                "휴일정보",
                listOf()
            )
        )
    }
    val todo = TodoEvent(
        "소현이 약속",
        EventType.DAY,
        "설명 칸",
        dateConverter.dateToString(currDate),
        dateConverter.dateToString(currDate),
        true,
        30L,
        dateConverter.dateID(currDate),
        false
    )
    HaruAlarmTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MainColor)
                    .padding(innerPadding)
            ){
                Text(text = "다음 일정", modifier = Modifier.padding(start = 6.dp), fontSize = 18.sp)
                AlarmItemView().AlarmItemExpand(onCompleteClick = {}, todoEvent = todo, padding = 6.dp)
                CalendarViewPreview()
                Text(text = "광고", modifier = Modifier.fillMaxWidth(), fontSize = 18.sp)
            }
        }
    }
}