package com.huann305.baseapp.ui.screen.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.huann305.baseapp.data.local.db.AppDatabase
import com.huann305.baseapp.data.model.Item
import com.huann305.baseapp.data.repo.ItemRepository
import kotlinx.coroutines.launch

class MyViewModel(private val repo: ItemRepository) : ViewModel() {

    private val _list = MutableLiveData<List<Item>>()
    val list: LiveData<List<Item>> get() = _list

    init {
        getAll()
    }

    private fun getAll() {
        viewModelScope.launch {
            repo.getAllItems { items ->
                _list.value = items
            }
        }
    }

    fun insert(item: Item) {
        repo.insertItem(item) {
            if (it != null) {
                _list.value = _list.value?.plus(it)
            }
        }
    }

    fun delete(item: Item) {
        repo.deleteItem(item) {
            if (it != null) {
                _list.value = _list.value?.minus(it)
            }
        }
    }
}
