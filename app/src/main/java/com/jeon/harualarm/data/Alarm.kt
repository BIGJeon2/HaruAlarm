package com.jeon.harualarm.data

data class Alarm(
    val time: String,
    val isEnabled: Boolean,
    val daysOfWeek: List<String>
)