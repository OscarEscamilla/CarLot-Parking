package com.example.carlot.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class User{

    @SerializedName("id")
    @Expose
    private var id: Int? = null

    @SerializedName("correo")
    @Expose
    private var correo: String? = null

    @SerializedName("password")
    @Expose
    private var password: String? = null

    @SerializedName("image")
    @Expose
    private var image: String? = null

    @SerializedName("rol")
    @Expose
    private var rol: String? = null

    @SerializedName("id_person")
    @Expose
    private var id_person: Int? = null

    @SerializedName("nombre")
    @Expose
    private var nombre: String? = null

    @SerializedName("apellidos")
    @Expose
    private var apellido: String? = null

    @SerializedName("telefono")
    @Expose
    private var telefono: String? = null

    constructor(
        id: Int?,
        correo: String?,
        password: String?,
        image: String?,
        rol: String?,
        id_person: Int?,
        nombre: String?,
        apellido: String?,
        telefono: String?
    ) {
        this.id = id
        this.correo = correo
        this.password = password
        this.image = image
        this.rol = rol
        this.id_person = id_person
        this.nombre = nombre
        this.apellido = apellido
        this.telefono = telefono
    }

    fun getId(): Int? {
        return id
    }

    fun setId(id: Int?) {
        this.id = id
    }


    fun getCorreo(): String? {
        return correo
    }

    fun setCorreo(correo: String?) {
        this.correo = correo
    }

    fun getPassword(): String? {
        return password
    }

    fun setPassword(password: String?) {
        this.password = password
    }


    fun getImage(): String? {
        return password
    }

    fun setImage(image: String?) {
        this.image = image
    }


    fun getRol(): String? {
        return rol
    }

    fun setRol(rol: String?) {
        this.rol = rol
    }

    fun getIdPerson(): Int? {
        return id
    }

    fun setIdPerson(id_person: Int?) {
        this.id_person = id_person
    }


    fun getNombre(): String? {
        return nombre
    }

    fun setNombre(nombre: String?) {
        this.nombre = nombre
    }

    fun getApellido(): String? {
        return apellido
    }

    fun setApellido(apellido: String?) {
        this.apellido = apellido
    }


    fun getTelefono(): String? {
        return telefono
    }

    fun setTelefono(telefono: String?) {
        this.telefono = telefono
    }



    /*
    {
    "id": 3,
    "correo": "oscarescamilla@gmail.com",
    "password": "oscar123",
    "image": "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcR5UcrY71t1Gn2xal2VAWbV_tpkmAcbNIWYxrs_L23zGSo6wKt2",
    "rol": "1",
    "id_person": 3,
    "nombre": "Oscar",
    "apellido": "Escamilla",
    "telefono": "7753434890"
}
     */

}