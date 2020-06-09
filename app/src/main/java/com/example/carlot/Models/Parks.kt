package com.example.carlot.Models

class Parks (){

    var id: Int? = null
    var name: String? = null
    var calle: String? = null
    var colonia: String? = null
    var numero_ext: Int? = null
    var stock: Int? = null
    var dia_ini: String? = null
    var dia_fin: String? = null
    var hora_apertura: String? = null
    var hora_cierre: String? = null
    var descripcion: String? = null
    var id_person: Int? = null
    var longitud: Int? = null
    var latitud: Int? = null
    var image: String? = null
    var tarifa: String? = null

    var data: Parks? = null



    constructor(id: Int,
                name: String,
                calle: String,
                colonia: String,
                numero_ext: Int,
                stock: Int,
                dia_ini: String,
                dia_fin: String,
                hora_apertura: String,
                hora_cierre: String,
                descripcion: String,
                id_person: Int,
                longitud: Int,
                latitud: Int,
                image: String,
                tarifa: String) : this() {

        this.id = id
        this.name = name
        this.calle = calle
        this.colonia = colonia
        this.numero_ext = numero_ext
        this.stock = stock
        this.dia_ini = dia_ini
        this.dia_fin = dia_fin
        this.hora_apertura = hora_apertura
        this.hora_cierre = hora_cierre
        this.descripcion = descripcion
        this.id_person = id_person
        this.longitud = longitud
        this.latitud = latitud
        this.image = image
        this.tarifa = tarifa


    }



}


/*
"id": 6,
        "nombre_park": "Asap Rocky Park",
        "calle": "10 de mayo",
        "colonia": "Las lajas",
        "numero_ext": 145,
        "stock": 90,
        "dia_ini": "Lunes",
        "dia_fin": "Viernes",
        "hora_apertura": "08:30:00",
        "hora_cierre": "17:00:00",
        "descripcion": "Esta es una descripcion",
        "id_person": 18,
        "longitud": null,
        "latitud": null,
        "image": "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQM90jj1zxd3Ws8dXzi5bfjzCsP9V1NXbuThC4_DqHKus1hO5bY",
        "tarifa": "16"

*/
