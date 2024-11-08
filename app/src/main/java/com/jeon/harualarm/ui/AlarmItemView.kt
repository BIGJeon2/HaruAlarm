package com.jeon.harualarm.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jeon.database.Entity.TodoEvent
import com.jeon.harualarm.ui.theme.HaruAlarmTheme
import com.jeon.harualarm.ui.theme.MainColor
import com.jeon.harualarm.ui.theme.white
import com.jeon.harualarm.util.DateConverter
import com.jeon.model.vo.EventType
import java.util.Calendar

class AlarmItemView() {
    @Composable
    fun AlarmItemBasic(todoEvent: TodoEvent){
        var alarmEnabled by remember { mutableStateOf(todoEvent.isAlarm) }
        var viewState by remember {
            mutableStateOf(false)
        }
        Card(
            modifier = Modifier.padding(top = 6.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(5.dp))
                    .background(white)
                    .padding(6.dp)
                    .clickable {
                        viewState = !viewState
                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        modifier = Modifier.weight(6f),
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        Row {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = todoEvent.title,
                                fontSize = 16.sp,
                                style = MaterialTheme.typography.labelLarge,
                                color = Color.Black,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                    Switch(
                        modifier = Modifier.weight(1f),
                        checked = alarmEnabled,
                        onCheckedChange = { alarmEnabled = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color(0xFFA4E2A6),
                            uncheckedThumbColor = Color(0xFF625b71)
                        )
                    )
                }
                if(viewState){
                    Column {
                        HorizontalDivider(thickness = 1.dp)
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 6.dp),
                            text = todoEvent.description,
                            fontSize = 12.sp,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun AlarmItemExpand(onCompleteClick: (TodoEvent) -> Unit, todoEvent: TodoEvent, padding: Dp){
        Card(
            modifier = Modifier.padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(5.dp))
                    .background(white)
                    .padding(6.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        modifier = Modifier.weight(6f),
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        Column(
                            modifier = Modifier.padding(6.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = todoEvent.title,
                                fontSize = 16.sp,
                                style = MaterialTheme.typography.labelLarge,
                                color = Color.Black,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Start
                            )
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 6.dp),
                                text = todoEvent.description,
                                fontSize = 12.sp,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Black
                            )
                        }
                    }
                    TextButton(
                        onClick = {
                            onCompleteClick(todoEvent)
                        },
                        modifier = Modifier.weight(1.5f),
                        colors = ButtonColors(MainColor, Color.Black, Color.LightGray, Color.Black)
                    ){
                        Text(text = "완료")
                    }
                }
            }
        }
    }

}



@Preview
@Composable
fun AlarmItemPreview(){
    val date = Calendar.getInstance()
    val todo = TodoEvent(
        "Title",
        EventType.DAY,
        "Description",
        DateConverter().dateToString(date),
        DateConverter().dateToString(date),
        true,
        30L,
        DateConverter().dateID(date)
    )
    HaruAlarmTheme {
        Column {
            AlarmItemView().AlarmItemBasic(todo)
            AlarmItemView().AlarmItemExpand(onCompleteClick =  {

            },todo, 12.dp)
        }
    }
}