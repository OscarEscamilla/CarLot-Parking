package com.example.carlot.Views.Users

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.example.carlot.Models.Cars
import com.example.carlot.Models.Parks
import com.example.carlot.Models.User
import com.example.carlot.R
import com.example.carlot.Services.RetrofitClient
import com.example.carlot.Services.ServiceCarlot
import com.example.carlot.Utils.SessionManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback

class DetalleParkActivity : AppCompatActivity() {

    var park: Parks? = null
    var toolbar: Toolbar? = null
    var tv_name: TextView? = null
    var tv_tarifa: TextView? = null
    var tv_horarios: TextView? = null
    var tv_dias_habil: TextView? = null
    var tv_description: TextView? = null
    var img_park: ImageView? = null
    var btn_float_reserva: FloatingActionButton? = null
    var cars: ArrayList<Cars>? = null
    var user: User? =  null
    var gson: Gson? = null
    var sessionManager: SessionManager? = null

    var serviceCarLot: ServiceCarlot? = null

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

        tv_name = findViewById(R.id.tv_park_name)
        tv_tarifa = findViewById(R.id.tv_tarifa )
        tv_description = findViewById(R.id.tv_description )
        tv_dias_habil = findViewById(R.id.tv_dias_habil )
        tv_horarios = findViewById(R.id.tv_horario)
        img_park = findViewById(R.id.img_detalle_park)
        btn_float_reserva = findViewById(R.id.floatingActionButton)

        btn_float_reserva!!.setOnClickListener {
//            var intent: Intent = Intent(this, PaymentActivity::class.java)
//            startActivity(intent)
            showAlertReserva()
            //Snackbar.make(it, "Reserva...", Snackbar.LENGTH_SHORT).show()


        }

        serviceCarLot = RetrofitClient().getClientService()
        gson = Gson()
        sessionManager = SessionManager(getSharedPreferences("Carlot", Context.MODE_PRIVATE), applicationContext)
        user = sessionManager!!.getSession()

    }

    fun showAlertReserva(){
        var inflater = layoutInflater;
        var inflate_view = inflater.inflate(R.layout.alert_reserva, null)
        // init components from layorut inflate_view
        var time_reserva: TimePicker = inflate_view.findViewById(R.id.timePicker)
//        var sp_cars: Spinner = inflate_view.findViewById(R.id.sp_cars)
//        var cars = arrayOf("toyotas", "hummer", "ferrari")
//        var arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, cars)
//        sp_cars.adapter = arrayAdapter


        // end init componenst
        var horaReservaPicker: String? = null
        time_reserva.setOnTimeChangedListener { view, hourOfDay, minute ->
            horaReservaPicker = "$hourOfDay:$minute"
        }
        var alert = AlertDialog.Builder(this)
        alert.setView(inflate_view);
        alert.setCancelable(false)
        alert.setNegativeButton("Cancelar", null);
        alert.setPositiveButton("Siguiente"){dialog, which ->
            var intent: Intent = Intent(this, PaymentActivity::class.java)
            intent.putExtra("hora_arrivo",horaReservaPicker)
            intent.putExtra("id_park", park!!.id)
            intent.putExtra("id_person", user!!.id)
            //Log.i("data-spiner", sp_cars.selectedItem.toString())
            startActivity(intent)
        }



        var create_alert = alert.create()
        create_alert.show()

    }

    fun getCarsRetrofit() {
        var mCall = serviceCarLot?.getCars(user?.id.toString())
        mCall?.enqueue(object : Callback<List<Cars>> {
            override fun onResponse(
                call: Call<List<Cars>?>?,
                response: retrofit2.Response<List<Cars>?>?
            ) {
                if (response!!.isSuccessful()){
                    var data = JSONArray(gson?.toJson(response.body()))
                    for (i in 0..data.length() -1){
                        var car = gson?.fromJson(data.get(i).toString(), Cars::class.java)
                        cars?.add(car!!)
                    }
                }
            }
            override fun onFailure(call: Call<List<Cars>?>, t: Throwable?) {
                if (call.isCanceled()) {

                }
            }
        })
    }

    fun setDataIntentToView(){
        tv_name?.text = this.park?.name
        tv_tarifa?.text = "$" + this.park?.tarifa + " x hora"
        tv_description?.text = this.park?.descripcion
        tv_dias_habil?.text = """${this.park?.dia_ini} - ${this.park?.dia_ini}"""
        tv_horarios?.text = """${this.park?.hora_apertura} a ${this.park?.hora_apertura}"""

        Picasso.get()
            .load(this.park!!.image)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(img_park);

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
            e.get("longitud") as String,
            e.get("latitud") as String,
            e.get("image") as String,
            e.get("tarifa")  as String)

        Log.i(TAG,  park?.name.toString())

    }
}
