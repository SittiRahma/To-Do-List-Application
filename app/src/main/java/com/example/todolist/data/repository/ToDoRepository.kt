package com.example.todolist.data.repository

import androidx.lifecycle.LiveData
import com.example.todolist.data.model.ToDoData
import com.example.todolist.data.ToDoDao

class ToDoRepository(private val toDoDao: ToDoDao) {

    val getAllData: LiveData<List<ToDoData>> = toDoDao.getAllData()
    val sortByHighPriority: LiveData<List<ToDoData>> = toDoDao.sortByHighPriority
    val sortByLowPriority: LiveData<List<ToDoData>> = toDoDao.sortByLowPriority()

    suspend fun inserData(toDoData: ToDoData){
        toDoDao.insertData(toDoData)
    }

    suspend fun updateData(toDoData: ToDoData){
        toDoDao.updateData(toDoData)
    }

    suspend fun deleteData(toDoData: ToDoData){
        toDoDao.deleteData(toDoData)
    }

    suspend fun deleteAll(toDoData: ToDoData){
        toDoDao.deleteAll()
    }

    fun searchData(searchQuery: String): LiveData<List<ToDoData>> {
        return toDoDao.searchData(searchQuery)
    }
}