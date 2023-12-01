package com.example.rutastecmadero

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.SeekBar
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment

class ConfigFragment : Fragment() {
    lateinit var btnReset : Button
    lateinit var seekBarFont: SeekBar
    lateinit var tglMarc : SwitchCompat
    lateinit var tglRamp : SwitchCompat
    lateinit var tglRut : SwitchCompat
    lateinit var chkBox : CheckBox
    var progresoBarra = 0
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
        }
    }
}