package com.jeon.database.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jeon.model.VO.EventType

@Entity(tableName = "todo_event_table")
data class TodoEvent(
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
    val dateID: String
) {
    @PrimaryKey(autoGenerate = true)
    var eventId: Long = 0
}
