package com.huann305.baseapp.data.remote.instance

import com.huann305.baseapp.data.remote.iinterface.CatApiService
import com.huann305.baseapp.data.remote.iinterface.ItemApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "http://192.168.31.162:3000/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val itemApi: ItemApiService by lazy {
        retrofit.create(ItemApiService::class.java)
    }

    val catApi: CatApiService by lazy {
        retrofit.create(CatApiService::class.java)
    }
}
