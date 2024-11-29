package com.jeon.database.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jeon.database.vo.EventType

@Entity(tableName = "todo_event_table")
data class TodoEventDTO(
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "type")
    var eventType: EventType,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "start_date")
    var startDate: String,
    @ColumnInfo(name = "end_date")
    var endDate: String,
    @ColumnInfo(name = "alarm_enable")
    var isAlarm: Boolean,
    @ColumnInfo(name = "alarm_rate")
    var alarmRate: Long,
    @ColumnInfo(name = "date_id")
    val dateID: String,
    @ColumnInfo(name = "isComplete")
    var isComplete: Boolean = false,

    ) {
    @PrimaryKey(autoGenerate = true)
    var eventId: Long = 0
}
