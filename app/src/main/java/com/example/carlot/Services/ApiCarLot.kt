package com.example.carlot.Services

import com.example.carlot.Models.User
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface ApiCarLot {

    @POST("/login")
    @FormUrlEncoded
    fun getUser(
        @Field("password") password : String?,
        @Field("email") email : String? ): Call<User?>?






}