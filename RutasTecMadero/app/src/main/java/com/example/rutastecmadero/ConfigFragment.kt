package com.example.rutastecmadero

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.SeekBar
import android.widget.Spinner
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap


class ConfigFragment : Fragment() {
    lateinit var btnReset : Button
    lateinit var seekBarFont: SeekBar
    lateinit var tglMarc : SwitchCompat
    lateinit var tglRamp : SwitchCompat
    lateinit var tglRut : SwitchCompat
    lateinit var chkBox : CheckBox
    lateinit var spinner : Spinner
    var typeMap = GoogleMap.MAP_TYPE_NORMAL
    var opcSpinner = 0
    var progresoBarra = 848
    var colorActual = 0
    var firstColor = true
    var init = false
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("toggleMarcador",tglMarc.isEnabled)
        outState.putSerializable("toggleRampa",tglRamp.isEnabled)
        outState.putSerializable("toggleRuta",tglRut.isEnabled)
        outState.putSerializable("progress",progresoBarra)
        outState.putSerializable("bgColor",colorActual)
        outState.putSerializable("checkDev",chkBox.isChecked)
        outState.putSerializable("opcSpinner",opcSpinner)
    }

    @Suppress("DEPRECATION")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(savedInstanceState != null) {
            tglMarc.isEnabled = savedInstanceState.getSerializable("toggleMarcador") as Boolean
            tglRamp.isEnabled = savedInstanceState.getSerializable("toggleRampa") as Boolean
            tglRut.isEnabled = savedInstanceState.getSerializable("toggleRuta") as Boolean
            progresoBarra = savedInstanceState.getSerializable("progress") as Int
            colorActual = savedInstanceState.getSerializable("bgColor") as Int
            chkBox.isChecked = savedInstanceState.getSerializable("checkDev") as Boolean
            opcSpinner = savedInstanceState.getSerializable("opcSpinner") as Int
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_config, container, false)
    }

    override fun onStart() {
        super.onStart()
        init = true
        btnReset = requireActivity().findViewById(R.id.btnReset) as Button
        seekBarFont = requireActivity().findViewById(R.id.seekbar_font) as SeekBar
        tglMarc = requireActivity().findViewById(R.id.marcs) as SwitchCompat
        tglRamp = requireActivity().findViewById(R.id.ramps) as SwitchCompat
        tglRut = requireActivity().findViewById(R.id.ruts) as SwitchCompat
        chkBox = requireActivity().findViewById(R.id.checkTEST) as CheckBox
        spinner = requireActivity().findViewById(R.id.mapType) as Spinner
        val datos = requireActivity().resources.getStringArray(R.array.tipoMapas)

        val spnAdapter = ArrayAdapter(requireActivity().baseContext,android.R.layout.simple_spinner_item,datos)
        spnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spnAdapter

        spinner.setSelection(opcSpinner)

        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
            var opc = spnAdapter.getItem(position)
            when(opc){
                    "Normal"-> selectedMap(0,GoogleMap.MAP_TYPE_NORMAL)
                    "Satelite"-> selectedMap(1,GoogleMap.MAP_TYPE_SATELLITE)
                    "Hibrido"-> selectedMap(2,GoogleMap.MAP_TYPE_HYBRID)
                    "Terrenal"-> selectedMap(3,GoogleMap.MAP_TYPE_TERRAIN)
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        })

        seekBarFont.setMax(256 * 7 - 1);
        if(firstColor){
            seekBarFont.progress = 848
            colorActual = Color.argb(255, 0, 212, 212)
            seekBarFont.setBackgroundColor(colorActual)
            firstColor = false
        }else{
            seekBarFont.progress = progresoBarra
            seekBarFont.setBackgroundColor(colorActual)
            // En caso de cargar mal el color por defecto
            if(colorActual== 0){
                seekBarFont.progress = 848
                seekBarFont.setBackgroundColor((Color.argb(255, 0, 212, 212)))
            }
        }
        seekBarFont.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    var r = 0
                    var g = 0
                    var b = 0
                    if (progress < 256) {
                        b = progress
                    } else if (progress < 256 * 2) {
                        g = progress % 256
                        b = 256 - progress % 256
                    } else if (progress < 256 * 3) {
                        g = 255
                        b = progress % 256
                    } else if (progress < 256 * 4) {
                        r = progress % 256
                        g = 256 - progress % 256
                        b = 256 - progress % 256
                    } else if (progress < 256 * 5) {
                        r = 255
                        g = 0
                        b = progress % 256
                    } else if (progress < 256 * 6) {
                        r = 255
                        g = progress % 256
                        b = 256 - progress % 256
                    } else if (progress < 256 * 7) {
                        r = 255
                        g = 255
                        b = progress % 256
                    }
                    progresoBarra = progress
                    colorActual = Color.argb(255, r, g, b)
                    seekBarFont.setBackgroundColor(colorActual)

                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }


        })

        btnReset.setOnClickListener{
            seekBarFont.progress = 848
            colorActual = Color.argb(255, 0, 212, 212)
            seekBarFont.setBackgroundColor(colorActual)
            tglMarc.isChecked=true
            tglRamp.isChecked=true
            tglRut.isChecked=true
            chkBox.isChecked=false
            firstColor=true

            typeMap = GoogleMap.MAP_TYPE_NORMAL
            spinner.setSelection(0)
        }
    }

    private fun selectedMap(indx: Int,map : Int){
        opcSpinner = indx
        typeMap = map
    }
}