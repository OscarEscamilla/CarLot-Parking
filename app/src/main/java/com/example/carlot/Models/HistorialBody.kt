package com.example.carlot.Models

import com.google.gson.annotations.SerializedName

data class HistorialBody(
    @SerializedName("nombre_park") val nombre_park : String,
    @SerializedName("id_park") val id_park : Int,
    @SerializedName("id_input") val id_input : Int,
    @SerializedName("entrada") val entrada : String,
    @SerializedName("salida") val salida : String,
    @SerializedName("id_car") val id_car : Int,
    @SerializedName("matricula") val matricula : String,
    @SerializedName("marca") val marca : String,
    @SerializedName("color") val color : String
)





