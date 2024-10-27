package com.jeon.database.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "holidays"
)
data class Holiday(
    @ColumnInfo(name = "year")
    val year: Int,
    @PrimaryKey
    val date: String,
    @ColumnInfo(name = "description")
    val description: String
)
