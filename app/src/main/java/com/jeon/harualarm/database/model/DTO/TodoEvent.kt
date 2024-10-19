package com.jeon.harualarm.database.model.DTO

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jeon.harualarm.database.model.VO.Type
import java.time.LocalDateTime
import java.util.Date

@Entity(tableName = "period_event")
data class TodoEvent(
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "type")
    var eventType: Type,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "start_date")
    var startTime: Date,
    @ColumnInfo(name = "end_date")
    var endTime: Date,
    @ColumnInfo(name = "alarm_enable")
    var isAlarm: Boolean,
    @ColumnInfo(name = "alarm_rate")
    var alarmRate: Long,
    @ColumnInfo(index = true)
    val calendarDateId: Long // Foreign Key
) {
    @PrimaryKey(autoGenerate = true)
    var eventId: Long = 0
}
