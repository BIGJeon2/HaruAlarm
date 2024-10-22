package com.jeon.harualarm.database.model.DTO

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity

@Entity(
    tableName = "holidays"
)
data class Holiday(
    @ColumnInfo(name = "year")
    val year: Int,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "description")
    val description: String
)