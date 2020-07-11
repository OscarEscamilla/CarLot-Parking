package com.example.carlot.Services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClient {

    private var retrofit: Retrofit? = null

    final private var baseUrl = "http://carlotapinode.herokuapp.com"

    fun getClientService(): ServiceCarlot? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        var client: ServiceCarlot? = retrofit?.create(ServiceCarlot::class.java)


        return client
    }
}