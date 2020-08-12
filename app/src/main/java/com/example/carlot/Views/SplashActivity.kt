
package com.example.carlot.Views

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.carlot.Models.User
import com.example.carlot.R
import com.example.carlot.Utils.SessionManager
import com.example.carlot.Views.Parks.HomeParkActivity
import com.example.carlot.Views.Users.MainActivity

class SplashActivity : AppCompatActivity() {

    var sessionManager: SessionManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        //validarSession()
        getDataFirstStart()
        startApp()
        timer()


    }






    // obtiene toda la informacion que es necesaria para iniciar la aplicacion y optimizacion
    fun getDataFirstStart(){


    }

    // instacia la clase session manager y valida session para redirigir al login o a home de acuerdo al usuario
    fun validarSession(){
        sessionManager = SessionManager(getSharedPreferences("Carlot", Context.MODE_PRIVATE), applicationContext)
        var user: User? = sessionManager?.getSession()

        Log.e("user-validate", user?.rol.toString())

        when(user?.rol?.toInt()){
            1 -> startActivity(Intent(this, MainActivity::class.java))

            0 -> startActivity(Intent(this, HomeParkActivity::class.java))

            else -> startActivity(Intent(this, LoginActivity::class.java))
        }

//        if (user?.rol?.toInt() == 1){
//            startActivity(Intent(this, MainActivity::class.java))
//        }else{
//            startActivity(Intent(this, LoginActivity::class.java))
//        }
    }

    // inicia el activity validado en el metodo "validarSession"
    fun startApp(){

    }

    fun timer(){
        val SPLASH_TIME_OUT: Long = 3000
        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity
            validarSession()
            // close this activity
            finish()
        }, SPLASH_TIME_OUT)

    }
}