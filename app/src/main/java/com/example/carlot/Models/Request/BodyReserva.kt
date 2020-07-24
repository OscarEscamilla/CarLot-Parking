package com.example.carlot.Models.Request

class BodyReserva ( id_park: Int, id_person: Int, hra_arrivo: String){




    var id_park: Int? = null
    var id_person: Int? = null
    var hra_arrivo: String? = null


    init {

        this.id_park = id_park
        this.id_person = id_person
        this.hra_arrivo = hra_arrivo
    }

}