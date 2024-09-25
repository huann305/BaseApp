package com.huann305.baseapp.data.model

import com.google.gson.annotations.SerializedName

data class Cat(
    @SerializedName("_id")
    val id: String,
    val mimetype: String,
    val tags: List<String>
)
