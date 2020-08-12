package com.example.carlot.Services

import com.example.carlot.Models.Cars
import com.example.carlot.Models.HistorialBody
import com.example.carlot.Models.Request.BodyReserva
import com.example.carlot.Models.Request.Status
import com.example.carlot.Models.User
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
    fun createReserva(@Body bodyReserva: BodyReserva): Call<BodyReserva>


    @GET("/get_cars/{id}")
    fun getCars(@Path("id") id: String): Call<List<Cars>>


    @GET("/get_historial/{id}")
    fun getHistorial(@Path("id") id: String): Call<List<HistorialBody>>


}