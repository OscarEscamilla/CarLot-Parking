package com.example.carlot.Views.Users

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carlot.Models.Cars
import com.example.carlot.Models.HistorialBody
import com.example.carlot.Models.User
import com.example.carlot.R
import com.example.carlot.Services.RetrofitClient
import com.example.carlot.Services.ServiceCarlot
import com.example.carlot.Utils.SessionManager
import com.example.carlot.Views.Adapters.CarsAdapter
import com.example.carlot.Views.Adapters.HistorialAdapter
import com.google.gson.Gson
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback

class HistorialActivity : AppCompatActivity() {


    var itemsHistorial = ArrayList<HistorialBody>()
    var adapterHistorial: HistorialAdapter? = null
    var gson: Gson? = null
    var sessionManager: SessionManager? = null
    var user: User? = null
    // api service
    var serviceCarLot: ServiceCarlot? = null
    var progressBar: ProgressBar? = null
    var toolbar: Toolbar? = null
    var rc_historial: RecyclerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial)

        viewComponents()

        // init session manager
        sessionManager = SessionManager(getSharedPreferences("Carlot", Context.MODE_PRIVATE), applicationContext)
        user = sessionManager!!.getSession() // retorna objeto de la clase usuario
        // init variables
        serviceCarLot = RetrofitClient().getClientService()
        gson = Gson()

        getHistorial()
    }




    fun viewComponents(){
        toolbar = findViewById(R.id.toolbar_historial)
        setSupportActionBar(toolbar)
        rc_historial = findViewById(R.id.rc_historial)
        progressBar = findViewById(R.id.progress_bar_his)
        // set layut manager to ReyclerView
        var linearLayoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        rc_historial?.layoutManager = linearLayoutManager
        rc_historial?.itemAnimator = DefaultItemAnimator()

    }

    fun getHistorial() {
        showProgresBar()
        var mCall = serviceCarLot?.getHistorial(user?.id.toString())
        mCall?.enqueue(object : Callback<List<HistorialBody>> {
            override fun onResponse(
                call: Call<List<HistorialBody>?>?,
                response: retrofit2.Response<List<HistorialBody>?>?
            ) {
                if (response!!.isSuccessful()){
                    Log.e("response-historial", "success")
                    var data = JSONArray(gson?.toJson(response.body()))
                    for (i in 0..data.length() -1){
                        var user = gson?.fromJson(data.get(i).toString(), HistorialBody::class.java)
                        itemsHistorial.add(user!!)
                    }
                    // init adapter
                    adapterHistorial = HistorialAdapter( itemsHistorial!! , applicationContext)
                    hideProgresBar()
                    rc_historial?.adapter = adapterHistorial
                }
            }
            override fun onFailure(call: Call<List<HistorialBody>?>, t: Throwable?) {
                if (call.isCanceled()) {
                    Log.e("response-historial", "fail")
                }
            }
        })
    }


    fun showProgresBar(){

    }

    fun hideProgresBar(){
        progressBar?.visibility = View.GONE
    }

}