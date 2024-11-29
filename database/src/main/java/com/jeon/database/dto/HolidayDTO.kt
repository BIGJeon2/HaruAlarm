package com.jeon.database.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "holidays"
)
data class HolidayDTO(
    @ColumnInfo(name = "year")
    val year: Int,
    @PrimaryKey
    val date: String,
    @ColumnInfo(name = "description")
    val description: String
)
