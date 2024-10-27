package com.jeon.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jeon.database.DAO.TodoEventDao
import com.jeon.database.DAO.HolidayDAO
import com.jeon.database.Entity.TodoEvent
import com.jeon.database.Entity.Holiday
import com.jeon.database.repository.HolidayRepository
import com.jeon.database.repository.TodoEventRepository

@Database(
    entities = [Holiday::class, TodoEvent::class],
    version = 1,
    exportSchema = false
)
abstract class CalendarDatabase :RoomDatabase(){
    abstract fun holidayDao(): HolidayDAO
    abstract fun todoEventDao(): TodoEventDao
}