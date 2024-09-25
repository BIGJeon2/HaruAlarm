package com.jeon.harualarm.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeon.harualarm.database.InternalDatabase
import com.jeon.harualarm.database.Type
import com.jeon.harualarm.database.data.Todo
import com.jeon.harualarm.database.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class JobsScreenViewModel(application: Application): ViewModel() {
    private val repository: TodoRepository
    var todoList: SnapshotStateList<Todo> = mutableStateListOf()
        private set

    init {
        val database = InternalDatabase.getDatabase(application).todoDao()
        repository = TodoRepository(database)
        getAllTodoList()
    }

    fun addTodoList(time: Date){
        val newTodo = Todo().apply {
            type = Type.DAY
            name = time.toString()
            description = "Description"
            isAlarmEnabled = true
            creationDate = time
        }
        viewModelScope.launch(Dispatchers.IO){
            repository.insertToDoList(newTodo)
            getAllTodoList()
        }
    }

    private fun getAllTodoList(){
        viewModelScope.launch(Dispatchers.IO) {
            val todos = repository.getAllToDoList()
            // 기존 todoList를 초기화하고 새로운 데이터를 추가합니다.
            todoList.clear()
            todoList.addAll(todos)
        }
    }

    fun deleteTodo(todo: Todo){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteToDoList(todo)
        }
    }


}