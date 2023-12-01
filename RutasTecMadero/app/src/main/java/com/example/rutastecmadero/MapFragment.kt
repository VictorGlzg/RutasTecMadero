package com.example.rutastecmadero

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.maps.model.RoundCap

class MapFragment : Fragment(), GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {
    private lateinit var mMap: GoogleMap
    private lateinit var supportMapFragment : SupportMapFragment
    //VARS PARA CONFIGURAR
    private var rutas = true
    private var rampas = true
    private var marcadores = true
    private var c = 0
    private var configSet = false
    private var zoomLock = true
    private var tipoMapa = 0
    private var lastCamara = LatLng(22.257027863336113, -97.85040616776612)

    companion object{
        const val REQUEST_CODE_LOCATION = 0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Initialize view
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        supportMapFragment = childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment
        //onMapReady
        supportMapFragment.getMapAsync{
            mMap = it
            this.mMap.setOnMapClickListener(this)
            this.mMap.setOnMapLongClickListener(this)
            if(zoomLock){
                mMap.setMinZoomPreference(17.8f)
            }else{
                mMap.setMinZoomPreference(5f)
            }
            if(rampas)
                drawPolygon()
            if(rutas)
                createPolylines()

            mMap.moveCamera(CameraUpdateFactory.newLatLng(lastCamara))

            if(marcadores){
                val lugares = arrayListOf<String>("Unidad Académica de Sistemas","Laboratorio de computo","Unidad de Industrial","Biblioteca"
                ,"Laboratorios III y IV Petroquímica","Laboratorios I y II Petroquímica", "Edificio HH", "Edificio QQ", "Edificio EE Aulas de Posgrado",
                    "Edificio NN Área administrativa")
                val coords = arrayListOf<LatLng>(LatLng(22.256373686780847, -97.8506313771422),LatLng(22.256833502186403, -97.85099832845263),
                    LatLng(22.257036644749537, -97.85137640837048),LatLng(22.2571049098605, -97.85075950028491),
                    LatLng(22.257457475313984, -97.8504157188205),LatLng(22.257457475313984, -97.8504157188205),
                    LatLng(22.25769516116672, -97.8503634157406),LatLng(22.257508363705455, -97.8500254573988),
                    LatLng(22.257471128300374, -97.84969353402981),LatLng(22.25736500734525, -97.84979344631954))

                for (i in 0 until lugares.size){
                    var posAct = coords.get(i)
                    var nombre = lugares.get(i)
                    var options = MarkerOptions().position(posAct).title(nombre)
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
                    mMap.addMarker(options)
                }
            }
            if(tipoMapa != 0){
                mMap.mapType = tipoMapa
            }
            enableLocation()
        }
        return view
    }

    private fun createPolylines(){
        if(c==0){
            c = ContextCompat.getColor(requireActivity().baseContext,R.color.cyan)
        }
        //Trazar las rutas del campus 2
        val polylineOptions = PolylineOptions()
            //entrada rumbo a UAS
            .add(LatLng(22.256610343391174, -97.84996197669707))
            .add(LatLng(22.256637985766744, -97.85000974053254))
            .add(LatLng(22.25662023944399, -97.8500874424538))
            .add(LatLng(22.256400808003363, -97.85021635687629))
            .add(LatLng(22.256536942522885, -97.85049413390621))
            .add(LatLng(22.256430262167527, -97.85055661299623))
            //camino hacia biblioteca
            .add(LatLng(22.256430262167527, -97.85055661299623))
            .add(LatLng(22.256536942522885, -97.85049413390621))
            .add(LatLng(22.256537933498763, -97.85049364542438))
            .add(LatLng(22.256681637209624, -97.85076698466938))
            .add(LatLng(22.256682266310577, -97.85076704466238))
            .add(LatLng(22.256899799040013, -97.85063344925713))
            .add(LatLng(22.256992074679673, -97.85081845136175))
            .add(LatLng(22.257038092023308, -97.85079126084298))
            //camino hacia sistemas
            .add(LatLng(22.257038092023308, -97.85079126084298))
            .add(LatLng(22.256992074679673, -97.85081845136175))
            .add(LatLng(22.25699247191386, -97.8508176295884))
            .add(LatLng(22.257025248948878, -97.85087819553304))
            .add(LatLng(22.256916497083395, -97.8509536209512))
            //camino hacia industrial
            .add(LatLng(22.256916497083395, -97.8509536209512))
            .add(LatLng(22.257025248948878, -97.85087819553304))
            .add(LatLng(22.257025329492876, -97.8508778357832))
            .add(LatLng(22.257214451907643, -97.85123880411443))
            .add(LatLng(22.257090342184696, -97.85131860480125))
            //regreso
            .add(LatLng(22.257090342184696, -97.85131860480125))
            .add(LatLng(22.257214451907643, -97.85123880411443))
            .add(LatLng(22.256899621338377,-97.85063335691588))
            .add(LatLng(22.256877930231028,-97.85059053019027))
            //hacia maestria
            .add(LatLng(22.257427956010844,-97.85024899893692))
            //labs 3 y 4 petroquimica
            .add(LatLng(22.257590959655104,-97.85055786828768))
            //labs 1 y 2 petroquimica
            .add(LatLng(22.257299728852885,-97.85001043702715))
            //ed QQ
            .add(LatLng(22.257434021224043,-97.8499072491608))
            //ed HH
            .add(LatLng(22.257765126323974,-97.85053103007746))

            .width(12f)
            .color(c)

        val polyline = mMap.addPolyline(polylineOptions)
        polyline.startCap = RoundCap()
        polyline.endCap = RoundCap()

    }

    override fun onMapClick(p0: LatLng) {
    }

    override fun onMapLongClick(p0: LatLng) {
    }

    private fun isLocationPermissionGranted()=
        ContextCompat.checkSelfPermission(requireActivity().baseContext, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED
        @SuppressLint("MissingPermission")
        private fun enableLocation(){
            if(!::mMap.isInitialized) return
            if(isLocationPermissionGranted()){
                mMap.isMyLocationEnabled = true
            }
            else{
                requestLocationPermission()
            }
        }

    private fun requestLocationPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)){
            Toast.makeText(requireActivity().baseContext,"Falta de autorizar los permisos de localizacion", Toast.LENGTH_SHORT).show()
        }else{
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),REQUEST_CODE_LOCATION)
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                mMap.isMyLocationEnabled = true
            }
            else{
                Toast.makeText(requireActivity().baseContext,"Activar la localizacion desde ajustes", Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    private fun drawPolygon(){
        mMap.clear()
        var polygon = PolygonOptions()
        .add(LatLng(22.256822042377024, -97.85043489343123))
        .add(LatLng(22.25676885872791, -97.850435947836))
        .add(LatLng(22.256722262028234, -97.85035106826841))
        .add(LatLng(22.256738363507154, -97.85032418095201))
        .add(LatLng(22.25677056646009, -97.85033235258744))
        .add(LatLng(22.256822042377024, -97.85043489343123))
        polygon.fillColor(R.color.cyan)
        mMap.addPolygon(polygon)

        polygon = PolygonOptions()
            .add(LatLng(22.256461518177602, -97.85032200239907))
            .add(LatLng(22.25643307003203, -97.85026300458387))
            .add(LatLng(22.256408630262328, -97.85027753688266))
            .add(LatLng(22.256438034487104, -97.85033724109017))
            .add(LatLng(22.256461518177602, -97.85032200239907))
        polygon.fillColor(R.color.cyan)
        mMap.addPolygon(polygon)

        polygon = PolygonOptions()
            .add(LatLng(22.256660498885566, -97.85070989436556))
            .add(LatLng(22.25664230935159, -97.8506754240073))
            .add(LatLng(22.256626007623694, -97.85068693566284))
            .add(LatLng(22.256643024372124, -97.85072302142284))
            .add(LatLng(22.256660498885566, -97.85070989436556))
        polygon.fillColor(R.color.cyan)
        mMap.addPolygon(polygon)

        polygon = PolygonOptions()
            .add(LatLng(22.256835985089907, -97.85054850248126))
            .add(LatLng(22.256791406054703, -97.85057741963466))
            .add(LatLng(22.25677468681161, -97.85054346237244))
            .add(LatLng(22.256819612682108, -97.85051605282715))
            .add(LatLng(22.256835985089907, -97.85054850248126))
        polygon.fillColor(R.color.cyan)
        mMap.addPolygon(polygon)

        polygon = PolygonOptions()
            .add(LatLng(22.256916389332204, -97.85093310587833))
            .add(LatLng(22.25690515780029, -97.85094137259516))
            .add(LatLng(22.256887983334536, -97.85090823859203))
            .add(LatLng(22.256918470735982, -97.85088800581383))
            .add(LatLng(22.25692639609747, -97.85090563914842))
            .add(LatLng(22.256907728277042, -97.85091803112456))
            .add(LatLng(22.256916389332204, -97.85093310587833))
        polygon.fillColor(R.color.cyan)
        mMap.addPolygon(polygon)

        polygon = PolygonOptions()
            .add(LatLng(22.256879928276987, -97.85052552056094))
            .add(LatLng(22.256915348157136, -97.85050268529974))
            .add(LatLng(22.25689949782418, -97.85047288431511))
            .add(LatLng(22.2568634464766, -97.85049661707))
            .add(LatLng(22.256879928276987, -97.85052552056094))
        polygon.fillColor(R.color.cyan)
        mMap.addPolygon(polygon)

        polygon = PolygonOptions()
            .add(LatLng(22.257021304580007, -97.8507649132593))
            .add(LatLng(22.256993188344666, -97.85078358641461))
            .add(LatLng(22.25698462532806, -97.8507662580681))
            .add(LatLng(22.257012562909892, -97.85074804632045))
            .add(LatLng(22.257021304580007, -97.8507649132593))
        polygon.fillColor(R.color.cyan)
        mMap.addPolygon(polygon)

        polygon = PolygonOptions()
            .add(LatLng(22.257043243185763, -97.85080725873011))
            .add(LatLng(22.257012889458125, -97.85082785596218))
            .add(LatLng(22.257004464228018, -97.85080832859755))
            .add(LatLng(22.257033861947335, -97.85078903604152))
            .add(LatLng(22.257043243185763, -97.85080725873011))
        polygon.fillColor(R.color.cyan)
        mMap.addPolygon(polygon)

        polygon = PolygonOptions()
            .add(LatLng(22.256455559699546, -97.85053007127347))
            .add(LatLng(22.25642703680721, -97.8505484434349))
            .add(LatLng(22.256419468092673, -97.85053363005206))
            .add(LatLng(22.256446557673783, -97.85051534450109))
            .add(LatLng(22.256455559699546, -97.85053007127347))
        polygon.fillColor(R.color.cyan)
        mMap.addPolygon(polygon)

        polygon = PolygonOptions()
            .add(LatLng(22.256951028993157, -97.85070155052995))
            .add(LatLng(22.256938223260292, -97.85071085797374))
            .add(LatLng(22.25692141055785, -97.85067953928238))
            .add(LatLng(22.256933384170864, -97.85067108565697))
            .add(LatLng(22.256951028993157, -97.85070155052995))
        polygon.fillColor(R.color.cyan)
        mMap.addPolygon(polygon)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadConfig(tglRut : Boolean, tglRam : Boolean, tglMarc : Boolean, colorRut : Int, zL : Boolean, map : Int){
        rutas = tglRut
        rampas = tglRam
        marcadores = tglMarc
        tipoMapa = map
        c = colorRut
        zoomLock=!zL
        configSet = true
    }
}