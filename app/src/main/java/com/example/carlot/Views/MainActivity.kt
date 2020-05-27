package com.example.carlot.Views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.carlot.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {


    var frame_contenedor: FrameLayout? = null
    var navigation_bottom: BottomNavigationView? = null

    var fragment: Fragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        alternateFragments()

    }


    fun initView() {
        frame_contenedor = findViewById(R.id.contenedor_fragments)
        navigation_bottom = findViewById(R.id.navigation_bottom)
        // fragment home init
        fragment = HomeFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.contenedor_fragments, fragment!!).commit()
    }

    fun alternateFragments(){
        navigation_bottom!!.setOnNavigationItemSelectedListener {item: MenuItem ->
            when(item.itemId){
                R.id.item_home -> {
                    Toast.makeText(this, "home", Toast.LENGTH_SHORT).show()
                    fragment = HomeFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.contenedor_fragments, fragment!!).commit()
                    true
                }
                R.id.item_parks -> {
                    Toast.makeText(this, "park", Toast.LENGTH_SHORT).show()
                    fragment = ParksFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.contenedor_fragments, fragment!!).commit()
                    true
                }
                R.id.item_profile -> {
                    Toast.makeText(this, "profile", Toast.LENGTH_SHORT).show()
                    fragment = ProfileFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.contenedor_fragments, fragment!!).commit()
                    true
                }else ->{
                    false
                }
            }
        }
        // transaccion de fragment replace

    }



}
