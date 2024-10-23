package com.jeon.harualarm.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jeon.harualarm.database.CalendarDatabase

class MainViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    private val database = CalendarDatabase.getDatabase(application)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalendarViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return CalendarViewModel(database.holidayDao(), database.eventDao()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}