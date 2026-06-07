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

class Registrar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var db : BaseSQLite

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etNombre = findViewById<EditText>(R.id.et_Nombre)
        val etCorreo = findViewById<EditText>(R.id.et_Correo)
        val etContra = findViewById<EditText>(R.id.et_Contra)
        val etRepeContra = findViewById<EditText>(R.id.et_RepeContra)

        val btnRegistrarUsuario = findViewById<Button>(R.id.btn_RegistrarUsuario)

        val tvIniciarSesion = findViewById<TextView>(R.id.tv_YaInicia)

        db = BaseSQLite(this) //Inicializamos la base de datos

        btnRegistrarUsuario.setOnClickListener {
            val nombre = etNombre.text.toString()
            val correo = etCorreo.text.toString()
            val contraA = etContra.text.toString()
            val contraB = etRepeContra.text.toString()

            if (nombre.isEmpty() || correo.isEmpty() || contraA.isEmpty() || contraB.isEmpty()) {
                Toast.makeText(this,
                    "Debe completar todos los campos para registrase",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                if (contraA == contraB){
                    db.insertarUsuario(nombre,correo,contraA)
                    Toast.makeText(this,
                        "Cuenta registrada exitosamente",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this, Iniciar_Sesion::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this,
                        "Las contraseñas no coinciden",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        tvIniciarSesion.setOnClickListener {
            val intent = Intent(this, Iniciar_Sesion::class.java)
            startActivity(intent)
        }
    }
}