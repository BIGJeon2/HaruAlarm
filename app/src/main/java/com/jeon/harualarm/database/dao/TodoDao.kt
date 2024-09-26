package com.jeon.harualarm.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jeon.harualarm.database.model.Todo

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo_list")
    suspend fun getAllTodoList(): List<Todo>

    @Insert
    suspend fun insertToDoList(todo: Todo)

    @Delete
    suspend fun deleteToDoList(todo: Todo)

    @Update
    suspend fun updateToDoList(todo: Todo)
}