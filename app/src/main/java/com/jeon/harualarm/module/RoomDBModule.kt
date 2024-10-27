package com.jeon.harualarm.module

import android.content.Context
import androidx.room.Room
import com.jeon.database.CalendarDatabase
import com.jeon.database.DAO.TodoEventDao
import com.jeon.database.DAO.HolidayDAO
import com.jeon.database.repository.HolidayRepository
import com.jeon.database.repository.TodoEventRepository
import com.jeon.rest_api.client.HolidayService
import com.jeon.rest_api.repository.HolidayApiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomDBModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) : CalendarDatabase = Room
        .databaseBuilder(context, CalendarDatabase::class.java, "calendar_database")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideHolidayRepository(calendarDatabase: CalendarDatabase): HolidayRepository = HolidayRepository(calendarDatabase.holidayDao())

    @Provides
    fun provideTodoEventRepository(calendarDatabase: CalendarDatabase): TodoEventRepository = TodoEventRepository(calendarDatabase.todoEventDao())

}