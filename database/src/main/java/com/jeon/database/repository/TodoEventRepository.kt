package com.jeon.database.repository

import androidx.annotation.WorkerThread
import com.jeon.database.dao.TodoEventDao
import com.jeon.database.dto.TodoEventDTO
import kotlinx.coroutines.flow.Flow

class TodoEventRepository(private val todoEventDao: TodoEventDao) {

    @WorkerThread
    fun getAllTodoEvents() : Flow<List<TodoEventDTO>> = todoEventDao.getAllEvent()

    @WorkerThread
    fun getEventList(dateID: String): List<TodoEventDTO> = todoEventDao.getEvent(dateID)

    @WorkerThread
    fun insertEvent(event: TodoEventDTO) = todoEventDao.insertEvent(event)

    @WorkerThread
    suspend fun deletedEvent(event: TodoEventDTO) = todoEventDao.deletedEvent(event)

    @WorkerThread
    suspend fun updateEvent(event: TodoEventDTO) = todoEventDao.updateEvent(event)

    @WorkerThread
    suspend fun getEventSize(dateID: String): Int = todoEventDao.getEventSize(dateID)

}