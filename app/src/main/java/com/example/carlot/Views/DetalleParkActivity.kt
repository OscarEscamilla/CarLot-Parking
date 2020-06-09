package com.example.carlot.Views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.carlot.Models.Parks
import com.example.carlot.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class DetalleParkActivity : AppCompatActivity() {

    var park: Parks? = null
    var toolbar: Toolbar? = null

    var img_park: ImageView? = null
    var btn_float_reserva: FloatingActionButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_park)
        cargarDataIntent()
        initComponents()
        setDataIntentToView()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    fun initComponents(){

        toolbar = findViewById(R.id.tbr_detalle_park)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar?.title = park!!.name

        img_park = findViewById(R.id.img_detalle_park)
        btn_float_reserva = findViewById(R.id.floatingActionButton)

        btn_float_reserva!!.setOnClickListener {

            Snackbar.make(it, "Reserva...", Snackbar.LENGTH_SHORT).show()
        }


    }

    fun setDataIntentToView(){
        Picasso.get()
            .load(this.park!!.image)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(img_park);
        /*
        Picasso.get()
            .load((this.park!!.image)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.car)
            .into(holder.img_park);*/
    }


    fun cargarDataIntent(){
        val TAG = "cargarDataIntent"
        var e = intent.extras
        park = Parks(e?.get("id") as Int,
            e.get("name") as String,
            e.get("calle") as String,
            e.get("colonia") as String,
            e.get("numero_ext") as Int,
            e.get("stock") as Int,
            e.get("dia_ini") as String,
            e.get("dia_fin") as String,
            e.get("hora_apertura") as String,
            e.get("hora_cierre") as String,
            e.get("descripcion") as String,
            e.get("id_person") as Int,
            e.get("longitud") as Int,
            e.get("latitud") as Int,
            e.get("image") as String,
            e.get("tarifa")  as String)

        Log.i(TAG,  park?.name.toString())

    }
}
