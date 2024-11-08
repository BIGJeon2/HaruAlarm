package com.jeon.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jeon.database.dao.TodoEventDao
import com.jeon.database.dao.HolidayDao
import com.jeon.database.Entity.TodoEvent
import com.jeon.database.Entity.Holiday

@Database(
    entities = [Holiday::class, TodoEvent::class],
    version = 2,
    exportSchema = false
)
abstract class CalendarDatabase :RoomDatabase(){
    abstract fun holidayDao(): HolidayDao
    abstract fun todoEventDao(): TodoEventDao
}