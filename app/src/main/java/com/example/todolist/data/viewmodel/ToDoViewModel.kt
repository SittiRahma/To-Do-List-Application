package com.example.todolist.data.viewmodel

import android.app.Application
import android.app.DownloadManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.ToDoDatabase
import com.example.todolist.data.model.ToDoData
import com.example.todolist.data.repository.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoViewModel (application: Application) : AndroidViewModel(application){

    private val toDoDao = ToDoDatabase.getDatabase(
        application
    ).toDoDao()

    private val repository : ToDoRepository

    val getAllData : LiveData<List<ToDoData>>
    val sortByHighPriority: LiveData<List<ToDoData>>
    val sortByLowPriority: LiveData<List<ToDoData>>

    init {
        repository = ToDoRepository(toDoDao)
        getAllData = repository.getAllData
        sortByHighPriority = repository.sortByHighPriority
        sortByLowPriority = repository.sortByLowPriority
    }

    fun insertData(toDoData: ToDoData){
        viewModelScope.launch (Dispatchers.IO) {
            repository.inserData(toDoData)
        }
    }

    fun updateData(toDoData: ToDoData){
        viewModelScope.launch (Dispatchers.IO) {
            repository.updateData(toDoData)
        }
    }

    fun deleteData(toDoData: ToDoData){
        viewModelScope.launch (Dispatchers.IO) {
            repository.deleteData(toDoData)
        }
    }

    fun deleteAll(toDoData: ToDoData){
        viewModelScope.launch (Dispatchers.IO) {
            repository.deleteAll(toDoData)
        }
    }
    fun searchData(searchQuery : String) : LiveData<List<ToDoData>> {
        return repository.searchData(searchQuery)
    }
}