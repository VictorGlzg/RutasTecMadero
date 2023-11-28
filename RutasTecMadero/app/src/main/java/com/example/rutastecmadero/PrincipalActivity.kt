package com.example.rutastecmadero

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.maps.model.RoundCap

class PrincipalActivity : AppCompatActivity(),OnMapReadyCallback, GoogleMap.OnMapClickListener,
    GoogleMap.OnMapLongClickListener {
    private val FINE_PERMISSION_CODE = 1
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    private lateinit var currentLocation : Location
    private lateinit var fragmentPrincipal : SupportMapFragment

    lateinit var btnChatbot : Button
    lateinit var btnConfig : Button

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

        btnChatbot = findViewById(R.id.chatbot)

        btnChatbot.setOnClickListener {
            replaceFragment(ChatbotFragment())
            Toast.makeText(this,"Presionado",Toast.LENGTH_SHORT).show()
        }

    }

    @Suppress("DEPRECATION")
    private fun replaceFragment(f : Fragment) {
        val fragmentPrincipal = supportFragmentManager
        val fragmentTransaction = fragmentPrincipal.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentPrincipal,f)
        fragmentTransaction.commit()
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation(){
        val task = fusedLocationProviderClient.getLastLocation()
        //ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), FINE_PERMISSION_CODE)
        task.addOnSuccessListener {
            if(it != null){
                currentLocation = it
                //Inicializar el fragmento del mapa
                fragmentPrincipal = supportFragmentManager.findFragmentById(R.id.fragmentPrincipal) as SupportMapFragment
                fragmentPrincipal.getMapAsync(this@PrincipalActivity)
            }
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
        this.mMap.setOnMapClickListener(this)
        this.mMap.setOnMapLongClickListener(this)

        createPolylines()

        val campus2 = LatLng(22.257027863336113, -97.85040616776612)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(campus2))

        var loc = LatLng(currentLocation.latitude,currentLocation.longitude)
        val uas = LatLng(22.256417723605228, -97.85059487755778)

        var options = MarkerOptions().position(uas).title("Unidad Academica de Sistemas")
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))

        mMap.addMarker(options)
        mMap.addMarker(MarkerOptions().position(loc))
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

    private fun createPolylines(){
        //Trazar las rutas del campus 2
        val polylineOptions = PolylineOptions()
            //entrada rumbo a UAS
            .add(LatLng(22.256618058457427, -97.84997241437556))
            .add(LatLng(22.256640399837003, -97.85007433832214))
            .add(LatLng(22.256407056363226, -97.85022722424202))
            .add(LatLng(22.256544207757777, -97.85048203410194))
            .add(LatLng(22.25644305085775, -97.85055378319592))
            //camino hacia biblioteca y centro de computo
            .add(LatLng(22.256544207757777, -97.85048203410194))
            .add(LatLng(22.256703079798328, -97.85075561942472))
            .add(LatLng(22.256910978489174, -97.85063626111089))
            .add(LatLng(22.25701709978844, -97.8508682721998))
            .add(LatLng(22.256906634340698, -97.85093465687554))

            .width(12f)
            .color(ContextCompat.getColor(this,R.color.cyan))

        val polyline = mMap.addPolyline(polylineOptions)
        polyline.startCap = RoundCap()
        polyline.endCap = RoundCap()

    }

}