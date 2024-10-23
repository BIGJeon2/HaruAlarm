package com.jeon.harualarm.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jeon.harualarm.database.CalendarDatabase

class MainViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalendarViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return CalendarViewModel(CalendarDatabase.getDatabase(application).holidayDao()) as T
        }else if (modelClass.isAssignableFrom(JobsScreenViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return JobsScreenViewModel(application) as T
            }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}