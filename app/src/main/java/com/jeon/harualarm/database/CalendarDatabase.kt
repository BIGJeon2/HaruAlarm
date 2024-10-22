package com.jeon.harualarm.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jeon.harualarm.database.model.DTO.CalenderDate
import com.jeon.harualarm.database.model.DTO.TodoEvent
import com.jeon.harualarm.database.converter.TodoDataConverter
import com.jeon.harualarm.database.model.DAO.EventDAO

@Database(
    entities = [TodoEvent::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TodoDataConverter::class)
abstract class CalendarDatabase :RoomDatabase(){
    abstract fun eventDat(): EventDAO

    companion object {
        @Volatile
        private var INSTANCE: CalendarDatabase? = null

        fun getDatabase(context: Context) : CalendarDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CalendarDatabase::class.java,
                    "calendar_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}