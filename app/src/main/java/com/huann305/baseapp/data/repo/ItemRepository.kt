package com.huann305.baseapp.data.repo

import android.util.Log
import com.huann305.baseapp.data.local.db.AppDatabase
import com.huann305.baseapp.data.model.Item
import com.huann305.baseapp.data.remote.instance.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemRepository(private val database: AppDatabase) {
    val TAG = "ItemRepository"
    private val apiService = RetrofitInstance.itemApi

    fun getAllItems(callback: (List<Item>) -> Unit) {
        val call = apiService.getItems()

        call.enqueue(object : Callback<List<Item>> {
            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                if (response.isSuccessful) {
                    val items = response.body()
                    if (items != null) {
                        insertItemsToDatabase(items)
                        callback(items)
                    } else {
                        callback(getAllFromDatabase())
                    }
                } else {
                    callback(getAllFromDatabase())
                }
            }

            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                callback(getAllFromDatabase())
            }
        })
    }

    fun insertItem(item: Item, callback: (Item?) -> Unit) {
        val call = apiService.insertItem(item)

        call.enqueue(object : Callback<Item> {
            override fun onResponse(call: Call<Item>, response: Response<Item>) {
                if (response.isSuccessful) {
                    val insertedItem = response.body()
                    if (insertedItem != null) {
                        insertNewItemToDatabase(insertedItem)
                        callback(insertedItem)
                    }
                }
            }

            override fun onFailure(call: Call<Item>, t: Throwable) {
                Log.e(TAG, "onFailure: ", t)
                callback(null)
            }
        })
    }

    fun deleteItem(item: Item, callback: (Item?) -> Unit) {
        val call = apiService.deleteItem(item.id)

        call.enqueue(object : Callback<Item> {
            override fun onResponse(call: Call<Item>, response: Response<Item>) {
                if (response.isSuccessful) {
                    deleteItemFromDatabase(item)
                    callback(item)
                }
            }

            override fun onFailure(call: Call<Item>, t: Throwable) {
                Log.e(TAG, "onFailure: ", t)
                callback(null)
            }
        })
    }

    private fun deleteItemFromDatabase(item: Item) {
        CoroutineScope(Dispatchers.IO).launch {
            database.iDao().delete(item)
        }
    }

    private fun insertNewItemToDatabase(items: Item) {
        CoroutineScope(Dispatchers.IO).launch {
            database.iDao().insertItem(items)
        }
    }

    private fun insertItemsToDatabase(items: List<Item>) {
        CoroutineScope(Dispatchers.IO).launch {
            database.iDao().insertItems(items)
        }
    }

    private fun getAllFromDatabase() : List<Item> {
        return database.iDao().getAll()
    }
}
