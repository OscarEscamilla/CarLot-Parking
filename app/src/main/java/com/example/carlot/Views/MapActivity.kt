package com.example.carlot.Views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.carlot.R
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        initComponets()
        // render map
        initViewMap()
    }


    // init fragment of map
    fun initViewMap() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    // init view components
    fun initComponets() {
        val toolbar: Toolbar = findViewById(R.id.toolbar_maps)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onBackPressed() {
        finish()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val ubicacion = LatLng(20.0874351, 20.0874351)
        val ubicacion_dos = LatLng(20.0852015, -98.3822969)
        mMap.addMarker(MarkerOptions().position(ubicacion).title("Catedral"))
        mMap.addMarker(MarkerOptions().position(ubicacion_dos).title("maker two"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ubicacion))
    }
}



