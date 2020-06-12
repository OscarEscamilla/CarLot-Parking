
package com.example.carlot.Views

import android.content.Intent
import android.net.wifi.p2p.WifiP2pManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.carlot.R
import java.util.*

class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        validarSession()
        getDataFirstStart()
        startApp()
        timer()
    }

    // obtiene toda la informacion que es necesaria para iniciar la aplicacion y optimizacion
    fun getDataFirstStart(){

    }

    // instacia la clase session manager y valida session para redirigir al login o a home
    fun validarSession(){

    }

    // inicia el activity validado en el metodo "validarSession"
    fun startApp(){

    }

    fun timer(){
        val SPLASH_TIME_OUT: Long = 3000
        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

            startActivity(Intent(this,LoginActivity::class.java))

            // close this activity
            finish()
        }, SPLASH_TIME_OUT)

    }
}