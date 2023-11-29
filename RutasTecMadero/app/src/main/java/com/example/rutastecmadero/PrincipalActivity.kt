package com.example.rutastecmadero

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.rutastecmadero.databinding.ActivityPrincipalBinding


class PrincipalActivity : AppCompatActivity(){
    lateinit var binding : ActivityPrincipalBinding
    //val bNavegationView = findViewById<BottomNavigationView>(R.id.navBar)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navBar.menu.findItem(R.id.mapaOpc).isChecked = true

        binding.navBar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.chatbotOpc -> replaceFragment(ChatbotFragment())
                R.id.mapaOpc -> replaceFragment(MapFragment())
                R.id.configOpc -> replaceFragment(ConfigFragment())
                else -> {}
            }
            true
        }

    }

    @Suppress("DEPRECATION")
    private fun replaceFragment(f : Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentPrincipal,f).commit()
    }

}