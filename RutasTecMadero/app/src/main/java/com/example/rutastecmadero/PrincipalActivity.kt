package com.example.rutastecmadero

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.rutastecmadero.databinding.ActivityPrincipalBinding


class PrincipalActivity : AppCompatActivity(){
    lateinit var binding : ActivityPrincipalBinding
    val cbFragment = ChatbotFragment()
    val mpFragment = MapFragment()
    val conFragment = ConfigFragment()

    //val bNavegationView = findViewById<BottomNavigationView>(R.id.navBar)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Base_Theme_RutasTecMadero)
        super.onCreate(savedInstanceState)

        binding = ActivityPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navBar.menu.findItem(R.id.mapaOpc).isChecked = true

        binding.navBar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.chatbotOpc -> loadChatBot()
                R.id.mapaOpc -> loadMap()
                R.id.configOpc -> replaceFragment(conFragment)
                else -> {}
            }
            true
        }

    }
    private fun loadChatBot(){
        if(cbFragment.init && conFragment.init){
            cbFragment.adapter.admin = conFragment.chkBox.isChecked
        }
        replaceFragment(cbFragment)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadMap(){
        if(conFragment.init){
            mpFragment.loadConfig(conFragment.tglRut.isChecked,conFragment.tglRamp.isChecked,conFragment.tglMarc.isChecked,
                conFragment.colorActual,conFragment.chkBox.isChecked, conFragment.typeMap)
        }
        replaceFragment(mpFragment)
    }

    @Suppress("DEPRECATION")
    private fun replaceFragment(f : Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentPrincipal,f).commit()
    }

}