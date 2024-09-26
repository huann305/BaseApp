package com.huann305.baseapp.data.remote.iinterface

import com.huann305.baseapp.data.model.Item
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ItemApiService {

    @GET("api/items")
    fun getItems(): Call<List<Item>>

    @POST("api/items")
    fun insertItem(@Body item: Item): Call<Item>

    @PUT("api/items/{id}")
    fun updateItem(@Path("id") id: String, @Body item: Item): Call<Item>

    @DELETE("api/items/{id}")
    fun deleteItem(@Path("id") id: String): Call<Item>
}
