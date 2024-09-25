package com.huann305.baseapp.data.remote

import com.huann305.baseapp.data.model.Cat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CatRepository {

    private val apiService: CatApiService = RetrofitInstance.api

    fun getCatsByTag(tag: String, callback: CatCallback) {
        val call = apiService.getCatsByTag(tag)

        call.enqueue(object : Callback<List<Cat>> {
            override fun onResponse(call: Call<List<Cat>>, response: Response<List<Cat>>) {
                if (response.isSuccessful) {
                    val cats = response.body()
                    if (cats != null) {
                        callback.onSuccess(cats)
                    } else {
                        callback.onError("Không có dữ liệu")
                    }
                } else {
                    callback.onError("Lỗi: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Cat>>, t: Throwable) {
                callback.onError(t.message ?: "Lỗi không xác định")
            }
        })
    }

    interface CatCallback {
        fun onSuccess(cats: List<Cat>)
        fun onError(errorMessage: String)
    }
}
