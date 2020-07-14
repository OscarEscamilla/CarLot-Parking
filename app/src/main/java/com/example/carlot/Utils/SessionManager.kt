package com.example.carlot.Utils

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.example.carlot.Models.User
import com.example.carlot.Views.LoginActivity
import com.google.gson.Gson

class SessionManager(sharedPreferences: SharedPreferences, context: Context) {

    val USER__SESSION_KEY = "user"
    var editor: SharedPreferences.Editor? = null
    var sharedPreferences: SharedPreferences? = null
    var user: User? = null
    var gson: Gson? = null
    var context: Context? = null

    init {
        this.sharedPreferences = sharedPreferences
        this.editor = sharedPreferences.edit()
        this.gson = Gson()
        this.context = context
    }



    fun saveSession(data: String){
        editor?.putString(USER__SESSION_KEY, data)
        editor?.commit()
    }

    fun deleteSession(){
        editor?.clear()
        editor?.commit()
        var i = Intent(context, LoginActivity::class.java)
        context?.startActivity(i)
    }

    fun getSession(): User? {
        if (sharedPreferences!!.contains(USER__SESSION_KEY)){
            var userString = sharedPreferences!!.getString(USER__SESSION_KEY,"")
            user = gson?.fromJson(userString, User::class.java)

        }
        return  user
    }



}