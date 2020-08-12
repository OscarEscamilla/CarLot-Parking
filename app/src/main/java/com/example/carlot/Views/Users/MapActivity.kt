package com.example.carlot.Views.Users

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.ArrayMap
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.carlot.Models.Parks
import com.example.carlot.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import org.json.JSONArray
import org.json.JSONObject
import java.math.BigDecimal


class MapActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap

    private val permisoFineLocation = android.Manifest.permission.ACCESS_FINE_LOCATION
    private val permisoCorseLocation = android.Manifest.permission.ACCESS_COARSE_LOCATION

    private val CODIGO_SOLICITUD = 100

    private var fusedLocationProviderClient: FusedLocationProviderClient? = null // accede a la ultima actualizacion conocida

    private var locationRequest: LocationRequest? = null

    // location request callback
    var callback: LocationCallback? = null

    var parks: ArrayList<Parks>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        initViewComponets()
            initViewMap()
        fusedLocationProviderClient = FusedLocationProviderClient(this)
        initLocationRequest()
    }
    // ejecuccion desdepues de metodo on create
    override fun onStart() {
        super.onStart()
        // valida si ya existe el permiso de ubicacion
        if (validarPermisoUbicacion()){
            // si existe la obtiene
            obtenerUbicacion()
        }else{
            // si no soliocita el permiso
            solicitarPermisoUbicacionContexto()
        }
    }
    // init fragment of map
    fun initViewMap() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }
    // init view components
    fun initViewComponets() {
        val toolbar: Toolbar = findViewById(R.id.toolbar_maps)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val cambioStyleMap = mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
        if(cambioStyleMap){
            // informar de error de cambio de estilo de mapa
        }

        //getParks()

    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        TODO("Not yet implemented")
    }

    fun getParks(){
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://carlotapinode.herokuapp.com/get_all_parks"
        parks = ArrayList<Parks>()
        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                var data = JSONArray(response)
                for (i in 0..data.length() - 1){
                    var item = JSONObject(data.get(i).toString())

                    var latitud: String = item.get("latitud") as String
                    var longitud: String = item.get("longitud") as String

                    Log.e("ParlLatLonBefore",  item.get("latitud").toString() + " " +  item.get("longitud").toString())

                    var convertLatitude: Double = latitud.toDouble()
                    var convertLongitude: Double = longitud.toDouble()


                    Log.e("ParlLatLonAfter",  convertLatitude.toString() + " " + convertLongitude.toString())

                    var ubicacion_park = LatLng(convertLatitude,convertLongitude )

                    mMap.addMarker(
                        MarkerOptions()
                            .position(ubicacion_park)
                            .title( item.get("nombre_park").toString())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.estacionamiento))
                    )

                    parks!!.add(
                        Parks(
                        item.get("id") as Int,
                        item.get("nombre_park") as String,
                        item.get("calle") as String,
                        item.get("colonia") as String,
                        item.get("numero_ext") as Int,
                        item.get("stock") as Int,
                        item.get("dia_ini") as String,
                        item.get("dia_fin") as String,
                        item.get("hora_apertura") as String,
                        item.get("hora_cierre") as String,
                        item.get("descripcion") as String,
                        item.get("id_person") as Int,
                        item.get("longitud") as String,
                        item.get("latitud") as String,
                        item.get("image") as String,
                        item.get("tarifa")  as String 
                    )
                    )
                }

            },
            Response.ErrorListener {
                Log.i("error_map_activity", "ERROR")
            })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    private fun initLocationRequest(){
        locationRequest = LocationRequest()
        // intervalo de tiempo de actualizacion de la ubicacion
        //locationRequest?.interval = 10000 // representacion en milisegundos equivalente a 10segundos
        //locationRequest?.fastestInterval = 5000 // maximo de actualizacion cada 5 segundos
        locationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY // alta precision en la ubicacion, ocupa mas bateria
    }




    private fun validarPermisoUbicacion(): Boolean{
        // validamos si tenemos acceso a la ubicacion precisa
        var ubicacionPrecisa = ActivityCompat.checkSelfPermission(this, permisoFineLocation) == PackageManager.PERMISSION_GRANTED
        // validamos si tenemos acceso a la ubicacion ordinaria
        var ubicacionOrdinaria = ActivityCompat.checkSelfPermission(this, permisoCorseLocation) == PackageManager.PERMISSION_GRANTED
        return ubicacionPrecisa && ubicacionOrdinaria // retorna true si se tiene acceso a las dos tipos de ubicaciones
    }


    @SuppressLint("MissingPermission")
    private fun obtenerUbicacion(){
        // obtiene la ultima ubicacion conocida
        /*
        fusedLocationProviderClient?.lastLocation?.addOnSuccessListener(this, object : OnSuccessListener<Location>{
            override fun onSuccess(location : Location?) {
                if (location != null){
                    Toast.makeText(this@MapActivity, location.latitude.toString()+"-"+ location.longitude, Toast.LENGTH_LONG).show()
                }
            }
        })*/
        // obtiene ubicacion en cada cambio
        callback =  object : LocationCallback (){
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)
                if(mMap != null){
                    // habilita boton de ubicacion
                    mMap.isMyLocationEnabled = true
                    mMap.uiSettings.isMyLocationButtonEnabled = true
                    for(ubicacion in locationResult!!.locations){
                        val ubicacion_dos = LatLng(ubicacion.latitude, ubicacion.longitude)
                        // marker add
                        mMap.addMarker(
                            MarkerOptions()
                                .position(ubicacion_dos)
                                .title("¡Aqui estas!")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.coche))
                        )

                        val zoomLevel = 15.0.toFloat()
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion_dos, zoomLevel))
                    }
                    getParks()
                }
            }
        }
        fusedLocationProviderClient?.requestLocationUpdates(locationRequest, callback, null)
    }

    private fun solicitarPermisoUbicacionContexto(){
        //
        var proveerContexto = ActivityCompat.shouldShowRequestPermissionRationale(this,permisoFineLocation)
        if (proveerContexto){
            // mostrar contexto al usuario
        }

        solicitarPermisoUbicacion()
    }

    private fun solicitarPermisoUbicacion(){
        // codigo de solicitud 100
        requestPermissions(arrayOf(permisoCorseLocation, permisoFineLocation), CODIGO_SOLICITUD)
    }

    // on request ejecuccion despues de solicitar permisos de ubicacion
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            // cuando es igual al codigo de solicitu de ubicacion
            CODIGO_SOLICITUD -> {
                // si en su posicion 0 que es la solicitud de ubicacion es permitido y el array de granted results es mayor a 0
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // obtener ubicacion
                    obtenerUbicacion()
                }else{
                    Toast.makeText(this, "No tenemos permiso de acceder a su ubicación", Toast.LENGTH_LONG).show()
                    finish()
                }
            }
        }
    }


    // detiene actualizaciones de ubicacion cuando el activity entra en pausa
    fun stopUpdatesLocation(){
        fusedLocationProviderClient?.removeLocationUpdates(callback)
    }

    override fun onPause() {
        super.onPause()
        stopUpdatesLocation()
    }


    data class Ubicaciones(val name: String, val latitud: Int, val longitud: Int)


}



