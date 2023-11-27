package com.example.rutastecmadero

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class PrincipalActivity : AppCompatActivity(),OnMapReadyCallback, GoogleMap.OnMapClickListener,
    GoogleMap.OnMapLongClickListener {
    lateinit var mMap: GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)
        //Inicializar el fragmento del mapa
        var mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
        this.mMap.setOnMapClickListener(this)
        this.mMap.setOnMapLongClickListener(this)

        var campus2 = LatLng(22.257027863336113, -97.85040616776612)
        var UAS = LatLng(22.256417723605228, -97.85059487755778)
        mMap.addMarker(MarkerOptions().position(UAS).title("Unidad Academica de Sistemas"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(campus2))
    }

    override fun onMapClick(p0: LatLng) {
        TODO("Not yet implemented")
    }

    override fun onMapLongClick(p0: LatLng) {
        TODO("Not yet implemented")
    }
}