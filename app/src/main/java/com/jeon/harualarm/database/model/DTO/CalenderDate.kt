package com.jeon.harualarm.database.model.DTO

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jeon.harualarm.api.model.DayType
import java.util.Date

@Entity(
    tableName = "calendar_dates"
)
data class CalenderDate(
    @ColumnInfo(name = "date")
    val date: Date,
    @ColumnInfo(name = "type")
    val type: DayType,
){
    @PrimaryKey(autoGenerate = true)
    var calendarId: Long = 0
}