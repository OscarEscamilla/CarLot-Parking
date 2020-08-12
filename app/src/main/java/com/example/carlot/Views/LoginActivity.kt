package com.example.carlot.Views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.carlot.Models.User
import com.example.carlot.R
import com.example.carlot.Utils.SessionManager
import com.example.carlot.Views.Parks.HomeParkActivity
import com.example.carlot.Views.Users.MainActivity
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    var et_email:TextInputEditText? = null
    var et_password:TextInputEditText? = null

    var progresBar: ProgressBar? = null

    var btn_login: Button? = null
    var btn_signup: Button? = null

    lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initViewComponents()

        // init shared prefereces
        val MY_PREFERENCES = "Carlot"
        sharedPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE)

        btn_login?.setOnClickListener {

            val str_email = et_email?.text.toString()
            val str_pass = et_password?.text.toString()

            if (TextUtils.isEmpty(str_email)){
                et_email?.error = "ingrese su email"

            }else if (TextUtils.isEmpty(str_pass)){
                et_password?.error = "ingrese su contraseña"
            }else{
                ocultarTeclado()
                progresBar?.visibility = View.VISIBLE
                validateUser(str_pass, str_email )
            }
        }

        btn_register?.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    fun initViewComponents(){
        et_email = findViewById(R.id.et_email)
        et_password = findViewById(R.id.et_password)
        btn_login = findViewById<Button>(R.id.btn_login)
        btn_signup = findViewById<Button>(R.id.btn_register)
        progresBar = findViewById(R.id.pb_login)
        progresBar?.visibility = View.GONE

    }

    fun signInGoogle(){

    }

    fun validateUser(password: String, email: String) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(applicationContext)
        val url = "https://carlotapinode.herokuapp.com/login_user/$password/$email"
        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            com.android.volley.Response.Listener<String> { response ->
                if (response.isNullOrEmpty()){
                    Toast.makeText(applicationContext,"Correo o contraseña incorrectos...", Toast.LENGTH_SHORT).show()
                    progresBar?.visibility = View.GONE
                }else{
                    Log.e("rsponseLogin", response)
                    // guardamos en shared preference
                    var sessionManager = SessionManager(sharedPreferences, applicationContext)
                    sessionManager.saveSession(response)
                    var user: User? = sessionManager.getSession()

                    Log.e("user-validate", user?.rol.toString())
                    progresBar?.visibility = View.GONE
                    finish()

                    when(user?.rol?.toInt()) {
                        1 -> startActivity(Intent(this, MainActivity::class.java))

                        0 -> startActivity(Intent(this, HomeParkActivity::class.java))
                    }
                    //gson.fromJson()
                }
            },
            com.android.volley.Response.ErrorListener {
                progresBar?.visibility = View.GONE
                Toast.makeText(applicationContext,"Correo o contraseña incorrectos...", Toast.LENGTH_SHORT).show()
            })
        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }


    fun ocultarTeclado(){
        var inputMethodManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(et_password?.windowToken, 0)
    }



}
















