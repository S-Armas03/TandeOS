package com.example.tandeos

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class activity_consejos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_consejos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val BottomNav = findViewById<BottomNavigationView>(R.id.bnv_bottom)
        BottomNav.selectedItemId = R.id.tab_consejos

        BottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.tab_consejos -> {
                    true
                }
                R.id.tab_calendario -> {
                    startActivity(Intent(this, activity_calendario::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.tab_perfil -> {
                    startActivity(Intent(this, activity_conf::class.java))
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