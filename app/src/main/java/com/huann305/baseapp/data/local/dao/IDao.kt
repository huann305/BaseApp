package com.huann305.baseapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.huann305.baseapp.data.model.Item

@Dao
interface IDao {
    @Query("SELECT * FROM item")
    fun getAll(): List<Item>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(items: List<Item>)
    @Insert
    fun insertItem(item: Item)

    @Query("SELECT * FROM item WHERE id = :id ORDER BY id DESC")
    fun getItemById(id: Int): Item?

    @Delete
    fun delete(item: Item): Int

    @Update
    fun updateItem(item: Item): Int
}