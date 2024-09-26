package com.huann305.baseapp.ui.screen.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.huann305.baseapp.data.local.dao.IDao
import com.huann305.baseapp.data.model.Item
import kotlinx.coroutines.launch

class MyViewModel(private val dao: IDao) : ViewModel() {
    private val _list = dao.getAll()
    val list: LiveData<List<Item>> get() = _list

    init {
        getAll()
    }

    private fun getAll(){
        viewModelScope.launch {
            _list.value?.plus(dao.getAll())
        }
    }

    fun insert(item: Item) {
        viewModelScope.launch {
            dao.insert(item)
        }
    }
    fun delete(item: Item) {
        viewModelScope.launch {
            dao.delete(item)
        }
    }
}
