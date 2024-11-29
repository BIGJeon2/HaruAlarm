package com.jeon.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jeon.database.dao.TodoEventDao
import com.jeon.database.dao.HolidayDao
import com.jeon.database.dto.TodoEventDTO
import com.jeon.database.dto.HolidayDTO

@Database(
    entities = [HolidayDTO::class, TodoEventDTO::class],
    version = 4,
    exportSchema = false
)
abstract class CalendarDatabase :RoomDatabase(){
    abstract fun holidayDao(): HolidayDao
    abstract fun todoEventDao(): TodoEventDao
}