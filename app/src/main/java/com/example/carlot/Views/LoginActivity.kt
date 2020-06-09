package com.example.carlot.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.carlot.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var btn_login = findViewById<Button>(R.id.btn_login)

        btn_login.setOnClickListener {
            var i: Intent = Intent(this, MainActivity::class.java)

            startActivity(i!!)
        }
    }


    fun validateUser(){

        var post_request: StringRequest? = null

        var url = "http://carlotapinode.herokuapp.com/login"

        post_request = StringRequest(
            Request.Method.POST,
            url,
            Response.Listener{ response ->

            },
            Response.ErrorListener {

            }
        )

        Volley.newRequestQueue(this).add(post_request)

    }




}
