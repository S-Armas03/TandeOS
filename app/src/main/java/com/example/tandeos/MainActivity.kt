package com.example.tandeos

import Simulacion_Repo.favoritos
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
//
import android.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
//
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val BottomNav = findViewById<BottomNavigationView>(R.id.bnv_bottom)
        BottomNav.selectedItemId = R.id.tab_favoritos

        BottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.tab_favoritos -> {
                    true
                }
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
                R.id.tab_perfil -> {
                    startActivity(Intent(this, activity_conf::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                    overridePendingTransition(0, 0)
                    true
                }
                /*  LINKEAR A LA PANTALLA  DE MAPA. HACER EN TODAS LAS PANTALLAS Y SUS POSIBILIDADES
                R.id.tab_perfil -> {

                    startActivity(Intent(this, activity_conf::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                    overridePendingTransition(0, 0)
                    true
                }
                */
                else -> false
            }
        }


        val repository = favoritos(this)
        val favoritos = repository.obtenerFavoritos()

        val listaFavoritos = findViewById<ListView>(R.id.list_Fav)
        val btnAgregar = findViewById<Button>(R.id.btnAgregar)

        fun cargarFavoritos() {
            val favoritos = repository.obtenerFavoritos()

            val datosLista = favoritos.map {
                "${it.nombre}\nEstado: ${it.estadoAgua}"
            }

            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                datosLista
            )

            listaFavoritos.adapter = adapter
        }
        //cargarFavoritos()

        fun mostrarColoniasDisponibles() {
            val coloniasDisponibles = repository.obtenerColoniasNoFavoritas()

            if (coloniasDisponibles.isEmpty()) {
                Toast.makeText(this, "No hay colonias disponibles", Toast.LENGTH_SHORT).show()
                return
            }

            val nombresColonias = coloniasDisponibles.map {
                "Colonia: ${it.nombre}"
            }.toTypedArray()

            AlertDialog.Builder(this)
                .setTitle("Agregar colonia favorita")
                .setItems(nombresColonias) { dialog, posicion ->
                    val coloniaSeleccionada = coloniasDisponibles[posicion]

                    repository.agregarFavorito(coloniaSeleccionada.id)

                    Toast.makeText(
                        this,
                        "${coloniaSeleccionada.nombre} agregada a favoritos",
                        Toast.LENGTH_SHORT
                    ).show()

                    cargarFavoritos()
                    dialog.dismiss()
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }

        btnAgregar.setOnClickListener {
            mostrarColoniasDisponibles()
        }
        cargarFavoritos()
    }
}