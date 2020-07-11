package com.example.carlot.Models

class UserFake {

    var id: Int? = null
    var name: String? = null
    var lastName: String? = null
    var nickName: String? = null




    constructor(
        id: Int,
        name: String,
        lastName: String,
        nickName: String
    ) {

        this.id = id
        this.name = name
        this.lastName = lastName
        this.nickName = nickName
    }

}