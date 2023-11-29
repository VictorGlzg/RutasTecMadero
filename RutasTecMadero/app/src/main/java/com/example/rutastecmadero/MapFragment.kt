package com.example.rutastecmadero

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.maps.model.RoundCap

class MapFragment : Fragment(), GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {
    private lateinit var mMap: GoogleMap
    private lateinit var supportMapFragment : SupportMapFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Initialize view
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        supportMapFragment = childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment

        supportMapFragment.getMapAsync{
            mMap = it
            this.mMap.setOnMapClickListener(this)
            this.mMap.setOnMapLongClickListener(this)

            createPolylines()

            val campus2 = LatLng(22.257027863336113, -97.85040616776612)
            mMap.moveCamera(CameraUpdateFactory.newLatLng(campus2))

            val uas = LatLng(22.256417723605228, -97.85059487755778)

            val options = MarkerOptions().position(uas).title("Unidad Academica de Sistemas")
            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))

            mMap.addMarker(options)
        }
        return view
    }

    private fun createPolylines(){
        //Trazar las rutas del campus 2
        val polylineOptions = PolylineOptions()
            //entrada rumbo a UAS
            .add(LatLng(22.256610343391174, -97.84996197669707))
            .add(LatLng(22.256637985766744, -97.85000974053254))
            .add(LatLng(22.25662023944399, -97.8500874424538))
            .add(LatLng(22.256400808003363, -97.85021635687629))
            .add(LatLng(22.256536942522885, -97.85049413390621))
            .add(LatLng(22.256430262167527, -97.85055661299623))
            //camino hacia biblioteca y centro de computo

            .width(12f)
            //.color(ContextCompat.getColor(this,R.color.cyan))

        val polyline = mMap.addPolyline(polylineOptions)
        polyline.startCap = RoundCap()
        polyline.endCap = RoundCap()

    }

    override fun onMapClick(p0: LatLng) {
    }

    override fun onMapLongClick(p0: LatLng) {
    }


}