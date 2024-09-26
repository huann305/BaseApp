package com.huann305.baseapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "item")
data class Item(
    @PrimaryKey
    @SerializedName("_id")
    val id: String,
    val name: String
)