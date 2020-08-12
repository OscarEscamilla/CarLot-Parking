package com.example.carlot.Models

import com.google.gson.annotations.SerializedName

//class Cars(id: Int,
//           matricula: String,
//           marca: String,
//           color: String,
//           descripcion: String,
//           idPerson: Int){
//
//    var id: Int? = null
//    var matricula: String? = null
//    var marca: String? = null
//    var color: String? = null
//    var descripcion: String? = null
//    var idPerson: Int? = null
//
//
//
//    init {
//        this.id = id
//        this.matricula = matricula
//        this.marca = marca
//        this.color = color
//        this.descripcion = descripcion
//        this.idPerson = idPerson
//    }
//
//}

data class Cars (

    @SerializedName("id") val id : Int,
    @SerializedName("matricula") val matricula : String,
    @SerializedName("marca") val marca : String,
    @SerializedName("color") val color : String,
    @SerializedName("descripcion") val descripcion : String,
    @SerializedName("id_person") val id_person : Int
)