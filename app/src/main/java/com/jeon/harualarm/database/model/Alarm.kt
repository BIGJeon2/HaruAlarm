package com.jeon.harualarm.database.model

data class Alarm(
    val time: String,
    val isEnabled: Boolean,
    val daysOfWeek: List<String>
)