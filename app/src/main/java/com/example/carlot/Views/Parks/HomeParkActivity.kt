package com.example.carlot.Views.Parks

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.carlot.Models.User
import com.example.carlot.R
import com.example.carlot.Services.RetrofitClient
import com.example.carlot.Utils.SessionManager
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class HomeParkActivity : AppCompatActivity() {


    var btn_cerrarsession: ImageButton? = null
    var img_user: ImageView? = null
    var tvUsername: TextView? = null
    var tvEmailUser: TextView? = null

    var gson: Gson? = null
    var sessionManager: SessionManager? = null
    var user: User? = null





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_park)

        sessionManager = SessionManager(getSharedPreferences("Carlot", Context.MODE_PRIVATE), applicationContext)

        btn_cerrarsession = findViewById(R.id.Button2)
        img_user = findViewById(R.id.user_photo)
        tvEmailUser = findViewById(R.id.user_email)
        tvUsername = findViewById(R.id.user_name)



        btn_cerrarsession?.setOnClickListener {
            sessionManager?.deleteSession()
            finish()
        }

        user = sessionManager!!.getSession() // retorna objeto de la clase usuario
        // init variables
        gson = Gson()

        initView()
    }



    fun initView(){

        tvUsername?.text = user?.nombre +  user?.apellido
        tvEmailUser?.text = user?.correo


        Picasso.get()
            .load(user?.image)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(img_user);
    }



}