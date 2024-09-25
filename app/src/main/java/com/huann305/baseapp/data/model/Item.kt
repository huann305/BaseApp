package com.huann305.baseapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String
)