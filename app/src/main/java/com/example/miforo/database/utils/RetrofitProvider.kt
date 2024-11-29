package com.example.miforo.database.utils

import com.example.miforo.data.PostServiceAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitProvider {

    companion object {
        fun getRetrofit(): PostServiceAPI {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(PostServiceAPI::class.java)
        }
    }
}