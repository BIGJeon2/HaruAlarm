package com.jeon.harualarm.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jeon.harualarm.database.converter.TodoDataConverter
import com.jeon.harualarm.database.dao.TodoDao
import com.jeon.harualarm.database.model.Todo

@Database(
    entities = [Todo::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TodoDataConverter::class)
abstract class InternalDatabase :RoomDatabase(){
    abstract fun todoDao(): TodoDao

    companion object {
        @Volatile
        private var INSTANCE: InternalDatabase? = null

        fun getDatabase(context: Context) : InternalDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    InternalDatabase::class.java,
                    "internal_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}