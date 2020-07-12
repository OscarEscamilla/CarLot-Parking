package com.example.carlot.Views

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.carlot.R
import com.example.carlot.Services.RetrofitClient
import com.example.carlot.Services.ServiceCarlot


class PaymentActivity : AppCompatActivity() {

    var btn_time_picker: ImageButton? = null
    var btn_confirmar: Button? = null

    var progressBar: ProgressBar? = null
    var txt_process: TextView? = null



    var hora: String? = null
    var placas: String? = null
    var id_park: String? = null

    var day = 0
    var month: Int = 0
    var year: Int = 0
    var hour: Int = 0
    var minute: Int = 0
    var myDay = 0
    var myMonth: Int = 0
    var myYear: Int = 0
    var myHour: Int = 0
    var myMinute: Int = 0

    var serviceClient: ServiceCarlot? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        serviceClient = RetrofitClient().getClientService()

        initViewComponents()

        cargarDataIntent()
/*
        btn_time_picker?.setOnClickListener{
            val calendar: Calendar = Calendar.getInstance()
            day = calendar.get(Calendar.DAY_OF_MONTH)
            month = calendar.get(Calendar.MONTH)
            year = calendar.get(Calendar.YEAR)

            hour = calendar.get(Calendar.HOUR)
            minute = calendar.get(Calendar.MINUTE)
            val datePickerDialog = TimePickerDialog(this, this , hour, minute, android.text.format.DateFormat.is24HourFormat(this))
            datePickerDialog.show()
        }*/
    }

    fun initViewComponents() {
        btn_confirmar = findViewById(R.id.btn_confimar)
        btn_confirmar?.setOnClickListener {
            Toast.makeText(applicationContext, "confirm", Toast.LENGTH_SHORT).show()
            //var peticion = Peticion()
            //peticion.execute()

        }

        txt_process?.visibility = View.GONE
        progressBar?.visibility = View.GONE

    }

    fun cargarDataIntent(){

        var extras = intent.extras

        placas =  extras?.get("placas").toString()
        hora =  extras?.get("hora_arrivo").toString()
        id_park = extras?.get("id_park").toString()

        Toast.makeText(this, "placas: $placas - hora: $hora", Toast.LENGTH_SHORT).show()

    }

//
//    class Peticion: AsyncTask<Void, Void, Void>() {
//
//        override fun doInBackground(vararg params: Void?): Void {
//            var service = RetrofitClient().getClientService()
//            var response: Call<List<UserFake?>?>? = service?.getUsersGet()
//
//            var iterator = response?.execute()?.body()
//
//            iterator!!.forEach {
//
//                Log.e("name:", it?.name )
//            }
//
//            //Log.e("response", response.toString())
//            TODO("Not yet implemented")
//        }
//
//    }





}





