package com.jeon.harualarm

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jeon.harualarm.viewmodels.CalendarViewModel

class MainViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CalendarViewModel(application) as T
        /*if (modelClass.isAssignableFrom(CalendarViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return CalendarViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")*/
    }
}