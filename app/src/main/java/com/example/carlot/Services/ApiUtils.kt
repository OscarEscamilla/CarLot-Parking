package com.example.carlot.Services




class ApiUtils {

    private fun ApiUtils() {}

    val BASE_URL = "http://carlotapinode.herokuapp.com"

    fun getAPIService(): ApiCarLot? {
        var cliente_retrofit = RetrofitClient()

        return cliente_retrofit.getClient(BASE_URL)?.create(ApiCarLot::class.java)

    }
}