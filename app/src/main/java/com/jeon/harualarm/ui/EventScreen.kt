package com.jeon.harualarm.ui

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jeon.harualarm.database.model.DAO.EventDAO
import com.jeon.harualarm.ui.theme.MainColor
import com.jeon.harualarm.ui.theme.SecondaryColor
import com.jeon.harualarm.viewmodels.CalendarViewModel
import com.jeon.harualarm.viewmodels.EventViewModel
import com.jeon.harualarm.viewmodels.EventViewModelInterface

class EventScreen(private val eventViewmodel: EventViewModelInterface, private val eventRepository: EventDAO) {

    @SuppressLint("AutoboxingStateCreation")
    @Composable
    fun TodoListContainer(viewModel: CalendarViewModel) {
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
    fun AlarmCard(viewModel: CalendarViewModel) {
        val alarmList = viewModel.todoList
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
                    onClick = { },
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
                        time = alarm.endDate,
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

}