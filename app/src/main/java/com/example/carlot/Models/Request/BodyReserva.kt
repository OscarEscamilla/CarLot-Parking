package com.example.carlot.Models.Request

class BodyReserva {
    var id: Int? = null
    var idPark: Int? = null
    var idPerson: Int? = null
    var hraArrivo: String? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}
    /**
     *
     * @param idPerson
     * @param idPark
     * @param id
     * @param hraArrivo
     */
    constructor(id: Int?, idPark: Int?, idPerson: Int?, hraArrivo: String?) : super() {
        this.id = id
        this.idPark = idPark
        this.idPerson = idPerson
        this.hraArrivo = hraArrivo
    }

}