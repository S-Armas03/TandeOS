package com.example.tandeos

import Simulacion_Repo.favoritos
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class activity_conf : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_conf)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val BottomNav = findViewById<BottomNavigationView>(R.id.bnv_bottom)
        BottomNav.selectedItemId = R.id.tab_perfil //Tab actual

        BottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                //actual
                R.id.tab_perfil -> {
                    true
                }
                //destino
                R.id.tab_calendario -> {
                    startActivity(Intent(this, activity_calendario::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.tab_consejos -> {
                    startActivity(Intent(this, activity_consejos::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.tab_favoritos -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                    overridePendingTransition(0, 0)
                    true
                }
                else -> false
            }
        }
    }
}