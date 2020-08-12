package com.example.carlot.Views.Users

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.carlot.Models.Request.BodyReserva
import com.example.carlot.R
import com.example.carlot.Services.RetrofitClient
import com.example.carlot.Services.ServiceCarlot
import com.google.gson.Gson
import com.stripe.android.Stripe
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.view.CardInputWidget
import io.card.payment.CardIOActivity
import io.card.payment.CreditCard
import okhttp3.OkHttpClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback


// stripe

import android.widget.Button
import androidx.core.app.NotificationCompat

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.lang.ref.WeakReference


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

    var CHANEL_ID = "com.example.carlot"
    var CHANEL_NAME = "android_chanel"
    // stripe
    private val backendUrl = "http://carlotapinode.herokuapp.com/"
    private val httpClient = OkHttpClient()
    private lateinit var paymentIntentClientSecret: String
    private lateinit var stripe: Stripe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        serviceClient = RetrofitClient().getClientService()
        gson = Gson()

        initViewComponents()

        cargarDataIntent()

        stripe = Stripe(applicationContext, "pk_test_51H8qEaINJiG0Kyse6MlxWHsVAqUIA06vljdjWhpzerGfXgplwaN7hp4Wks3S49jwv0UX6SsemtzgITmCvDkwUmHr00EttAuEyx")
        startCheckout()

    }

    private fun displayAlert(
        activity: Activity,
        title: String,
        message: String,
        restartDemo: Boolean = false
    ) {
        runOnUiThread {
            val builder = AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
            builder.setPositiveButton("Ok", null)
            builder.create().show()
        }
    }

    private fun startCheckout() {
        val weakActivity = WeakReference<Activity>(this)
        // Create a PaymentIntent by calling your server's endpoint.
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestJson = """
      {
          "currency":"usd",
          "amount": 300
      }
      """
        val body = requestJson.toRequestBody(mediaType)
        val request = okhttp3.Request.Builder()
            .url(backendUrl + "create-payment-intent")
            .post(body)
            .build()
        httpClient.newCall(request)
            .enqueue(object: okhttp3.Callback {
                override fun onFailure(call: okhttp3.Call, e: IOException) {
                    Log.i("response-stripe-fail", e.toString())
                    Toast.makeText(applicationContext, "Error on failure", Toast.LENGTH_SHORT).show()
                    weakActivity.get()?.let { activity ->
                        displayAlert(activity, "Failed to load page", "Error: $e")
                    }
                }
                override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                    if (!response.isSuccessful) {
                        Log.i("response-stripe-issuccess", response.toString())
                        Toast.makeText(applicationContext, "Error on is succesfull", Toast.LENGTH_SHORT).show()
                        weakActivity.get()?.let { activity ->
                            displayAlert(
                                activity,
                                "Failed to load page",
                                "Error: $response"
                            )
                        }
                    } else {
                        val responseData = response.body?.string()
                        val responseJson =
                            responseData?.let { JSONObject(it) } ?: JSONObject()
                        // For added security, our sample app gets the publishable key
                        // from the server.
                        //Toast.makeText(applicationContext, "Success pay", Toast.LENGTH_SHORT).show()
                        Log.i("response-stripe-success", responseJson.toString())
                        paymentIntentClientSecret = responseJson.getString("clientSecret")
                    }
                }
            })
        // Hook up the pay button to the card widget and stripe instance
        val payButton: Button = findViewById(R.id.btn_confimar)
        payButton.setOnClickListener {
            val cardInputWidget = findViewById<CardInputWidget>(R.id.cardInputWidget)
            cardInputWidget.paymentMethodCreateParams?.let { params ->
                Log.e("params", params.toString())
                val confirmParams = ConfirmPaymentIntentParams
                    .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret)
                stripe.confirmPayment(this, confirmParams)
            }
            createReserva()
            finish()


        }
    }

    fun initViewComponents() {
        btn_confirmar = findViewById(R.id.btn_confimar)

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

//        Toast.makeText(this, "id_park: $id_park - hora: $hora - id_person: $id_person", Toast.LENGTH_LONG).show()

    }

    fun createReserva(){
        var bodyReserva = BodyReserva(id_park?.toInt()!!, id_person?.toInt()!!, hora!!)
        var mCall = serviceClient?.createReserva(bodyReserva)
        mCall?.enqueue( object : Callback<BodyReserva>{
            override fun onResponse(call: Call<BodyReserva>?, response: retrofit2.Response<BodyReserva>?) {
                var data = gson?.toJson(response?.body())
                var reserva: BodyReserva = gson!!.fromJson(data, BodyReserva::class.java)
                Log.i("respose-body", reserva.hra_arrivo)
                //Toast.makeText(this@PaymentActivity, "Su reserva se genero con exito ahora puede ver el estatus en su perfil", Toast.LENGTH_LONG).show()
                showNotificationReserva()


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


    @SuppressLint("WrongConstant")
    fun showNotificationReserva(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mNotificationChannel = NotificationChannel(CHANEL_ID, CHANEL_NAME, importance)
            val notificationBuilder = Notification.Builder(applicationContext, CHANEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("CarLot")
                .setContentText("Su reserva se genero con exito ahora puede revisarla en su perfil")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mNotificationChannel)
            notificationManager.notify(0, notificationBuilder.build())
        } else {
            val notificationBuilder2 = NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("CarLot")
                .setContentText("Su reserva se genero con exito ahora puede revisarla en su perfil")

            val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(0, notificationBuilder2.build())
        }

    }

}





