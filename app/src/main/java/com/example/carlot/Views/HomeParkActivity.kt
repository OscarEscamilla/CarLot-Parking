package com.example.carlot.Views

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.carlot.R
import com.example.carlot.Utils.SessionManager

class HomeParkActivity : AppCompatActivity() {


    var btn_cerrarsession: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_park)

        btn_cerrarsession = findViewById(R.id.button2)

        btn_cerrarsession?.setOnClickListener {
            var sessionManager = SessionManager(getSharedPreferences("Carlot", Context.MODE_PRIVATE), applicationContext)
            sessionManager?.deleteSession()
            finish()
        }
    }



}