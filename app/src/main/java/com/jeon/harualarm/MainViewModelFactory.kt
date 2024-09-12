package com.jeon.harualarm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jeon.harualarm.viewmodels.CalendarViewModel

class MainViewModelFactory(): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalendarViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return CalendarViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}