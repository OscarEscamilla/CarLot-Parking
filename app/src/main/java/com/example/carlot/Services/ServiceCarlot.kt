package com.example.carlot.Services

import com.example.carlot.Models.CreateReservaBody
import com.example.carlot.Models.Status
import com.example.carlot.Models.User
import com.example.carlot.Models.UserFake
import retrofit2.Call
import retrofit2.http.*


interface ServiceCarlot {

    @FormUrlEncoded
    @POST("/login")
    fun getUser(
        @Field("password") password: String?,
        @Field("email") email: String?
    ): Call<User?>?


    @POST("/create_reserva")
    fun createReserva(@Body reserva: CreateReservaBody): Call<Status>


    @GET("usersFake")
    fun getUsersGet(): Call<List<UserFake?>?>?





}