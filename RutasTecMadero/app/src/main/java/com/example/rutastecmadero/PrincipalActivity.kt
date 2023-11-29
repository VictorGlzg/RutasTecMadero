package com.example.rutastecmadero

import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class PrincipalActivity : AppCompatActivity(){

    lateinit var btnChatbot : Button
    lateinit var btnMap : Button
    lateinit var btnConfig : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)
        //replaceFragment(MapFragment())

        btnChatbot = findViewById(R.id.chatbot)
        btnMap = findViewById(R.id.mapa)
        btnConfig = findViewById(R.id.conf)

        btnChatbot.setOnClickListener {
            replaceFragment(ChatbotFragment())
        }

        btnMap.setOnClickListener {
            replaceFragment(MapFragment())
        }

        btnConfig.setOnClickListener {
            replaceFragment(ConfigFragment())
        }

    }

    @Suppress("DEPRECATION")
    private fun replaceFragment(f : Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentPrincipal,f).commit()
    }





//    override fun onMapClick(p0: LatLng) {
//
//    }
//
//    override fun onMapLongClick(p0: LatLng) {
//
//    }


//    private fun createPolylines(){
//        //Trazar las rutas del campus 2
//        val polylineOptions = PolylineOptions()
//            //entrada rumbo a UAS
//            .add(LatLng(22.256618058457427, -97.84997241437556))
//            .add(LatLng(22.256640399837003, -97.85007433832214))
//            .add(LatLng(22.256407056363226, -97.85022722424202))
//            .add(LatLng(22.256544207757777, -97.85048203410194))
//            .add(LatLng(22.25644305085775, -97.85055378319592))
//            //camino hacia biblioteca y centro de computo
//            .add(LatLng(22.256544207757777, -97.85048203410194))
//            .add(LatLng(22.256703079798328, -97.85075561942472))
//            .add(LatLng(22.256910978489174, -97.85063626111089))
//            .add(LatLng(22.25701709978844, -97.8508682721998))
//            .add(LatLng(22.256906634340698, -97.85093465687554))
//
//            .width(12f)
//            .color(ContextCompat.getColor(this,R.color.cyan))
//
//        val polyline = mMap.addPolyline(polylineOptions)
//        polyline.startCap = RoundCap()
//        polyline.endCap = RoundCap()
//
//    }

}