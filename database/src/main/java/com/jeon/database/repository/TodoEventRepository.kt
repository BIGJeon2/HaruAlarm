package com.jeon.database.repository

import androidx.annotation.WorkerThread
import com.jeon.database.DAO.TodoEventDao
import com.jeon.database.Entity.TodoEvent
import kotlinx.coroutines.flow.Flow

class TodoEventRepository(private val todoEventDao: TodoEventDao) {

    val allTodoEvents : Flow<List<TodoEvent>> = todoEventDao.getAllEvent()

    @WorkerThread
    suspend fun getEventList(dateID: String): Flow<List<TodoEvent>> = todoEventDao.getEvent(dateID)

    @WorkerThread
    suspend fun insertEvent(event: TodoEvent) = todoEventDao.insertEvent(event)

    @WorkerThread
    suspend fun deletedEvent(event: TodoEvent) = todoEventDao.deletedEvent(event)

    @WorkerThread
    suspend fun updateEvent(event: TodoEvent) = todoEventDao.updateEvent(event)

    @WorkerThread
    suspend fun getEventSize(dateID: String): Int = todoEventDao.getEventSize(dateID)

}