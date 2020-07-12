package com.example.carlot.Utils

import android.content.Context
import android.content.SharedPreferences
import com.example.carlot.Models.User
import com.google.gson.Gson

class SessionManager(sharedPreferences: SharedPreferences) {

    val USER__SESSION_KEY = "user"
    var editor: SharedPreferences.Editor? = null
    var sharedPreferences: SharedPreferences? = null
    var user: User? = null
    var gson: Gson? = null

    init {
        this.sharedPreferences = sharedPreferences
        this.editor = sharedPreferences.edit()
        this.gson = Gson()
    }



    fun saveSession(data: String){
        editor?.putString(USER__SESSION_KEY, data)
        editor?.commit()
    }

    fun deleteSession(){

    }

    fun getSession(): User? {
        if (sharedPreferences!!.contains(USER__SESSION_KEY)){
            var userString = sharedPreferences!!.getString(USER__SESSION_KEY,"")
            user = gson?.fromJson(userString, User::class.java)

        }
        return  user
    }



}