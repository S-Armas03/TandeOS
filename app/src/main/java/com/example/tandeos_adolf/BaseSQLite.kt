package com.example.tandeos_adolf

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseSQLite (context: Context) : SQLiteOpenHelper (context, "TandeOSDB", null, 1){
    override fun onCreate(p0: SQLiteDatabase?) {
        if (p0 != null) {
            p0.execSQL("CREATE TABLE usuarios (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "usuario TEXT, correo TEXT, password TEXT)")
            p0.execSQL("CREATE TABLE tandeos (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "usuario TEXT, correo TEXT, password TEXT)")
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        if (p0 != null) {
            p0.execSQL("DROP TABLE IF EXISTS usuarios")
        }
    }

    fun insertarUsuario (usuario: String, correo: String, password: String){
        val db = writableDatabase
        val values = ContentValues().apply {
            put("usuario", usuario)
            put("correo", correo)
            put("password",password)
        }
        db.insert("usuarios",null, values)
    }

    fun iniciarSesionUsuario(identificador: String, password: String): Boolean {
        val db = readableDatabase

        val cursor = db.rawQuery("SELECT * FROM usuarios" +
                " WHERE (usuario = ? OR correo = ?) AND password = ?",
            arrayOf(identificador, identificador, password))

        val exito = cursor.count > 0
        cursor.close()
        return exito
    }
}