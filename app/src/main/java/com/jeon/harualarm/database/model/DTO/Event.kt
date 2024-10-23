package com.jeon.harualarm.database.model.DTO

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jeon.harualarm.database.model.VO.Type

@Entity(tableName = "todo_event_table")
data class Event(
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "type")
    var eventType: Type,
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
