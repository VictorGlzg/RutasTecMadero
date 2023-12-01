package com.example.rutastecmadero

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.rutastecmadero.databinding.ActivityPrincipalBinding
import java.io.Serializable


class PrincipalActivity : AppCompatActivity(){
    lateinit var binding : ActivityPrincipalBinding
    val cbFragment = ChatbotFragment()
    val mpFragment = MapFragment()
    val conFragment = ConfigFragment()

    //val bNavegationView = findViewById<BottomNavigationView>(R.id.navBar)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navBar.menu.findItem(R.id.mapaOpc).isChecked = true

        binding.navBar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.chatbotOpc -> replaceFragment(cbFragment)
                R.id.mapaOpc -> loadMap()
                R.id.configOpc -> replaceFragment(conFragment)
                else -> {}
            }
            true
        }

    }

    private fun loadMap(){
        if(conFragment.init){
            mpFragment.loadConfig(conFragment.tglRut.isChecked,conFragment.tglRamp.isChecked,conFragment.tglMarc.isChecked,
                conFragment.colorActual,conFragment.chkBox.isChecked)
        }
        replaceFragment(mpFragment)
    }

    @Suppress("DEPRECATION")
    private fun replaceFragment(f : Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentPrincipal,f).commit()
    }

}