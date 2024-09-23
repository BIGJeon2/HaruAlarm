package com.jeon.harualarm.database.converter

import androidx.room.TypeConverter
import java.util.Date

class TodoDataConverter {
    @TypeConverter
    fun fromTimeStamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimeStamp(date: Date?): Long? {
        return date?.time
    }
}