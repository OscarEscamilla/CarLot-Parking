package com.example.carlot.Views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.carlot.Models.Request.BodyReserva
import com.example.carlot.Models.Request.Status
import com.example.carlot.R
import com.example.carlot.Services.RetrofitClient
import com.example.carlot.Services.ServiceCarlot
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import io.card.payment.CardIOActivity
import io.card.payment.CreditCard
import retrofit2.Call
import retrofit2.Callback


class PaymentActivity : AppCompatActivity() {

    var btn_time_picker: ImageButton? = null
    var btn_confirmar: Button? = null

    var progressBar: ProgressBar? = null
    var txt_process: TextView? = null
    var btn_scan: Button? = null



    var hora: String? = null
    var placas: String? = null
    var id_park: String? = null
    var id_person: String? = null



    var serviceClient: ServiceCarlot? = null
    var gson: Gson? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        serviceClient = RetrofitClient().getClientService()
        gson = Gson()

        initViewComponents()

        cargarDataIntent()

    }


    fun initViewComponents() {
        btn_confirmar = findViewById(R.id.btn_confimar)
        btn_confirmar?.setOnClickListener {
            Toast.makeText(applicationContext, "confirm", Toast.LENGTH_SHORT).show()
            //var peticion = Peticion()
            //peticion.execute()
            createReserva()

        }
        btn_scan = findViewById(R.id.btn_scan)
        btn_scan?.setOnClickListener {
            var intent = Intent(this, CardIOActivity::class.java)
                .putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, false)
                .putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false)
                .putExtra(CardIOActivity.EXTRA_REQUIRE_CARDHOLDER_NAME, false)

            startActivityForResult(intent, 100)
        }
        txt_process?.visibility = View.GONE
        progressBar?.visibility = View.GONE
    }

    fun cargarDataIntent(){

        var extras = intent.extras

        //placas =  extras?.get("placas").toString()
        hora =  extras?.get("hora_arrivo").toString()
        id_park = extras?.get("id_park").toString()
        id_person = extras?.get("id_person").toString()

        Toast.makeText(this, "id_park: $id_park - hora: $hora - id_person: $id_person", Toast.LENGTH_LONG).show()

    }

    fun createReserva(){

        var bodyReserva = BodyReserva(id_park?.toInt()!!, id_person?.toInt()!!, hora!!)

        var mCall = serviceClient?.createReserva(bodyReserva)

        mCall?.enqueue( object : Callback<BodyReserva>{

            override fun onResponse(call: Call<BodyReserva>?, response: retrofit2.Response<BodyReserva>?) {
                var data = gson?.toJson(response?.body())
                var reserva: BodyReserva = gson!!.fromJson(data, BodyReserva::class.java)
                Log.i("respose-body", reserva.hra_arrivo)
                Toast.makeText(this@PaymentActivity, "Su reserva se genero con exito ahora puede ver el estatus en su perfil", Toast.LENGTH_LONG).show()
            }
            override fun onFailure(call: Call<BodyReserva>?, t: Throwable?) {
                Log.e("respose-bodyE", t.toString())
            }
        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100){
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)){

                var card: CreditCard = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT)

//                Log.e("number-card", card.cvv)
                Toast.makeText(this, "number-card" + card.cardNumber, Toast.LENGTH_LONG).show()

                if (card.isExpiryValid){
                    Log.e("expire-date", "${card.expiryMonth}/${card.expiryYear}")

                }


            }
        }
    }




}





