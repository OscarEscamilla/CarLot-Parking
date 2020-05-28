package com.example.carlot.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
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




}
