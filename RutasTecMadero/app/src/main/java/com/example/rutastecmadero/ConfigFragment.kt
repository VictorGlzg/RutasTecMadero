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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_config, container, false)
    }

    override fun onStart() {
        super.onStart()
        btnReset = requireActivity().findViewById(R.id.btnReset) as Button
        seekBarFont = requireActivity().findViewById(R.id.seekbar_font) as SeekBar
        tglMarc = requireActivity().findViewById(R.id.marcs) as SwitchCompat
        tglRamp = requireActivity().findViewById(R.id.ramps) as SwitchCompat
        tglRut = requireActivity().findViewById(R.id.ruts) as SwitchCompat
        chkBox = requireActivity().findViewById(R.id.checkTEST) as CheckBox


        seekBarFont.setMax(256 * 7 - 1);
        seekBarFont.progress = 848
        seekBarFont.setBackgroundColor((Color.argb(255, 0, 212, 212)))
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
                    seekBarFont.setBackgroundColor(Color.argb(255, r, g, b))
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }


        })

        btnReset.setOnClickListener{
            seekBarFont.progress = 848
            seekBarFont.setBackgroundColor((Color.argb(255, 0, 212, 212)))
            tglMarc.isChecked=true
            tglRamp.isChecked=true
            tglRut.isChecked=true
            chkBox.isChecked=false
        }
    }
}