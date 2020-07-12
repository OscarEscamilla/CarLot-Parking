package com.example.carlot.Models

class User {
    var id: Int? = null
    var correo: String? = null
    var password: String? = null
    var image: String? = null
    var rol: String? = null
    var idPerson: Int? = null
    var nombre: String? = null
    var apellido: String? = null
    var telefono: String? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param image
     * @param password
     * @param idPerson
     * @param correo
     * @param apellido
     * @param id
     * @param telefono
     * @param nombre
     * @param rol
     */
    constructor(
        id: Int?,
        correo: String?,
        password: String?,
        image: String?,
        rol: String?,
        idPerson: Int?,
        nombre: String?,
        apellido: String?,
        telefono: String?
    )  {
        this.id = id
        this.correo = correo
        this.password = password
        this.image = image
        this.rol = rol
        this.idPerson = idPerson
        this.nombre = nombre
        this.apellido = apellido
        this.telefono = telefono
    }
}