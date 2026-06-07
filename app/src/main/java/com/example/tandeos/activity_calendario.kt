package com.example.tandeos

import Simulacion_Repo.calendarioDB
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import models.Tandeo

class activity_calendario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_calendario)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val BottomNav = findViewById<BottomNavigationView>(R.id.bnv_bottom)
        BottomNav.selectedItemId = R.id.tab_calendario

        BottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.tab_calendario -> {
                    true
                }
                R.id.tab_favoritos -> {
                    startActivity(Intent(this, MainActivity::class.java))
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
                R.id.tab_consejos -> {
                    startActivity(Intent(this, activity_consejos::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                    overridePendingTransition(0, 0)
                    true
                }
                else -> false
            }
        }

        val repository = calendarioDB(this)
        // tandeos = repository.obtenerTandeos()
        var bandera = false

        val listaCalendario = findViewById<ListView>(R.id.list_Calendario)
        val btnHistorial = findViewById<Button>(R.id.btnHist)

        fun calendarioTandeos(tandeos: List<Tandeo>){
            val listaDatos = tandeos.map{
                "${it.nombreColonia}\nInicio: ${it.fechaInicio}\nFin: ${it.fechaFin}"
            }
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                listaDatos
            )
            listaCalendario.adapter = adapter
        }

        calendarioTandeos(repository.obtenerTandeos())

        btnHistorial.setOnClickListener {
            bandera = !bandera
            if(bandera){
                calendarioTandeos(repository.obtenerHistorial())
                btnHistorial.text = "Ver los actuales"
            } else {
                calendarioTandeos(repository.obtenerTandeos())
                btnHistorial.text = "Ver historial"
            }
        }
    }
}