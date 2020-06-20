package com.example.carlot.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.carlot.Models.User
import com.example.carlot.R
import com.example.carlot.Services.ApiUtils
import retrofit2.Callback

class LoginActivity : AppCompatActivity() {

    var api_utils = ApiUtils()
    var api_service = api_utils.getAPIService()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var btn_login = findViewById<Button>(R.id.btn_login)

        btn_login.setOnClickListener {
            var i: Intent = Intent(this, MainActivity::class.java)

            startActivity(i!!)
        }
    }

    fun validateUser(password: String, email: String ){

        //val enqueue = api_service!!.getUser(password, email)?.enqueue(callback)

    }
}















