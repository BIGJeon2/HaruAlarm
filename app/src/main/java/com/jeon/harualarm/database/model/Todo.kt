package com.jeon.harualarm.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jeon.harualarm.database.Type
import java.util.Date
@Entity(
    tableName = "todo_list"
)
data class Todo(
    @ColumnInfo(name = "type")
    var type: Type = Type.DAY,
    @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "description")
    var description: String = "",
    @ColumnInfo(name = "isAlarmed")
    var isAlarmEnabled: Boolean = false,
    @ColumnInfo(name = "creation_date")
    var creationDate: Date = Date()
){
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
