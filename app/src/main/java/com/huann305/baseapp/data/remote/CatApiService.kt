package com.huann305.baseapp.data.remote

import com.huann305.baseapp.data.model.Cat
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApiService {

    @GET("api/cats")
    fun getCatsByTag(@Query("tags") tag: String): Call<List<Cat>>
}
