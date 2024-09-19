package com.jeon.harualarm.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jeon.harualarm.database.Type
import java.util.Date
@Entity(
    tableName = "todo_list"
)
data class Todo(
    @PrimaryKey val date: Date,
    @ColumnInfo(name = "type") val type: Type,
    @ColumnInfo(name = "name")val name: String,
    @ColumnInfo(name = "description")val description: String,
    @ColumnInfo(name = "isAlarmed")val isAlarmEnabled: Boolean,
    @ColumnInfo(name = "creation_date")val creationDate: Date
)
