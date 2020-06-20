package com.example.carlot.Services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClient {

    private var retrofit: Retrofit? = null

    fun getClient(baseUrl: String?): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl("http://carlotapinode.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }
}