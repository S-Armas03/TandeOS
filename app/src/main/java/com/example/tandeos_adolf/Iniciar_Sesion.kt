package com.example.tandeos_adolf

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Iniciar_Sesion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var db : BaseSQLite

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_iniciar_sesion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val eTIdentificador = findViewById<EditText>(R.id.et_Usuario)
        val eTContra = findViewById<EditText>(R.id.et_Contrasena)

        val btnIniciar = findViewById<Button>(R.id.btn_IniciarSesion)

        val tvRegistrarse = findViewById<TextView>(R.id.tv_YaRegistra)

        db = BaseSQLite(this) //Inicializamos la base de datos

        btnIniciar.setOnClickListener {
            val id = eTIdentificador.text.toString()
            val contra = eTContra.text.toString()

            val identificacion = db.iniciarSesionUsuario(id,contra)

            if (identificacion == true){
                //Accede al mapa
            } else {
                Toast.makeText(this,
                    "Usuario no encontrado. \n Asegurese de que los datos sean correctos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        tvRegistrarse.setOnClickListener {
            val intent = Intent(this, Registrar::class.java)
            startActivity(intent)
        }
    }
}