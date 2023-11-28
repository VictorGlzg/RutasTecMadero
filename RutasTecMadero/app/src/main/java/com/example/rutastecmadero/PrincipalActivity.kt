package com.example.rutastecmadero

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class PrincipalActivity : AppCompatActivity(),OnMapReadyCallback, GoogleMap.OnMapClickListener,
    GoogleMap.OnMapLongClickListener {
    private val FINE_PERMISSION_CODE = 1
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    private lateinit var currentLocation : Location
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            when {
                permissions.getOrDefault(android.Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // Precise location access granted.
                }
                permissions.getOrDefault(android.Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    // Only approximate location access granted.
                } else -> {
                // No location access granted.
            }
            }
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()

        locationPermissionRequest.launch(arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION))

        //Inicializar el fragmento del mapa
//        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation(){
        val task = fusedLocationProviderClient.getLastLocation()
        //ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), FINE_PERMISSION_CODE)
        task.addOnSuccessListener {
            if(it != null){
                currentLocation = it
                //Inicializar el fragmento del mapa
                val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                mapFragment.getMapAsync(this@PrincipalActivity)
            }
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
        this.mMap.setOnMapClickListener(this)
        this.mMap.setOnMapLongClickListener(this)

        var loc = LatLng(currentLocation.latitude,currentLocation.longitude)
        val campus2 = LatLng(22.257027863336113, -97.85040616776612)
        val uas = LatLng(22.256417723605228, -97.85059487755778)

        var options = MarkerOptions().position(uas).title("Unidad Academica de Sistemas")
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))

        mMap.addMarker(options)
        mMap.addMarker(MarkerOptions().position(loc))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(campus2))
    }

    override fun onMapClick(p0: LatLng) {

    }

    override fun onMapLongClick(p0: LatLng) {

    }

    override fun onRequestPermissionsResult(rc:Int, perms:Array<String>, granResults:IntArray){
        super.onRequestPermissionsResult(rc,perms,granResults)
        if(rc == FINE_PERMISSION_CODE){
            if(granResults.isNotEmpty() && granResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation()
            } else{
                Toast.makeText(this,"Permiso de Localizacion denegado, porfavor  active el permiso.",Toast.LENGTH_SHORT).show()
            }
        }
    }
}