package com.jeon.harualarm.database

data class Alarm(
    val time: String,
    val isEnabled: Boolean,
    val daysOfWeek: List<String>
)