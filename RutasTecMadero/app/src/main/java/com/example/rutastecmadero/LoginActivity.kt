package com.example.rutastecmadero

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {
    lateinit var btnAceptar : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btnAceptar = findViewById(R.id.button)

        btnAceptar.setOnClickListener {
            val intent = Intent(this@LoginActivity, PrincipalActivity::class.java)
            startActivity(intent)
        }

    }
}