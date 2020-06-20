package com.example.carlot.Views

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.example.carlot.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnSuccessListener


class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap



    private val permisoFineLocation = android.Manifest.permission.ACCESS_FINE_LOCATION
    private val permisoCorseLocation = android.Manifest.permission.ACCESS_COARSE_LOCATION

    private val CODIGO_SOLICITUD = 100

    private var fusedLocationProviderClient: FusedLocationProviderClient? = null // accede a la ultima actualizacion conocida

    private var locationRequest: LocationRequest? = null

    // location request callback
    var callback: LocationCallback? = null

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        initViewComponets()
            initViewMap()
        fusedLocationProviderClient = FusedLocationProviderClient(this)
        initLocationRequest()


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
    }

    private fun initLocationRequest(){
        locationRequest = LocationRequest()
        // intervalo de tiempo de actualizacion de la ubicacion
        locationRequest?.interval = 10000 // representacion en milisegundos equivalente a 10segundos
        locationRequest?.fastestInterval = 5000 // maximo de actualizacion cada 5 segundos
        locationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY // alta precision en la ubicacion, ocupa mas bateria


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
                for(ubicacion in locationResult!!.locations){
                    val ubicacion_dos = LatLng(ubicacion.latitude, ubicacion.longitude)
                    mMap.addMarker(MarkerOptions().position(ubicacion_dos).title("¡Aqui estas!"))
                    val zoomLevel = 18.0.toFloat()
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion_dos, zoomLevel))
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
}



