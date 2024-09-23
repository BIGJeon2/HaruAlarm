package com.jeon.harualarm.database.repository

import com.jeon.harualarm.database.dao.TodoDao
import com.jeon.harualarm.database.data.Todo

class TodoRepository(private val todoDao: TodoDao) {
    suspend fun getAllToDoList() = todoDao.getAllTodoList()

    suspend fun updateToDoList(todo: Todo) = todoDao.updateToDoList(todo)

    suspend fun insertToDoList(todo: Todo) = todoDao.insertToDoList(todo)

    suspend fun deleteToDoList(todo: Todo) = todoDao.deleteToDoList(todo)


}