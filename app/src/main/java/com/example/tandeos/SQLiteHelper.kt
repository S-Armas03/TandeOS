package com.example.tandeos

import android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class SQLiteHelper(context: Context): SQLiteOpenHelper(context, "TandeOS", null, 2){
    override fun onCreate(db: SQLiteDatabase?){
        if(db != null){
            db.execSQL(
                "CREATE TABLE colonias" +
                        "(id_colonia INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "nombre TEXT NOT NULL," +
                        "estado_agua TEXT NOT NULL," +
                        "es_favorita INTEGER NOT NULL)"
            )
            db.execSQL(
                "CREATE TABLE tandeos" +
                    "(id_tandeo INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "id_colonia INTEGER NOT NULL,"+
                    "fecha_inicio TEXT NOT NULL," +
                    "fecha_fin TEXT NOT NULL,"+
                    "FOREIGN KEY (id_colonia) REFERENCES colonias (id_colonia))"
            )
            db.execSQL(
                "CREATE TABLE configuracion" +
                        "(id_configuracion INTEGER PRIMARY KEY," +
                        "notificaciones_tandeo INTEGER NOT NULL," +
                        "recordatorio_previo INTEGER NOT NULL," +
                        "sonido_alerta INTEGER NOT NULL)"
            )
            insertarDatos(db)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if(db!= null){
            db.execSQL("DROP TABLE IF EXISTS configuracion")
            db.execSQL("DROP TABLE IF EXISTS tandeos")
            db.execSQL("DROP TABLE IF EXISTS colonias")
            onCreate(db)
        }
    }
    private fun insertarDatos(db: SQLiteDatabase){
        db.execSQL(

            "INSERT INTO colonias (nombre, estado_agua, es_favorita)" +
            "VALUES ('Valle Verde', 'Con agua', 1)," +
            "('Privada del Sol', 'En tandeo', 1)," +
            "('Colonia Antigua', 'Sin agua', 1)," +
            "('Las Flores', 'Con agua', 0),"+
            "('Las Fuentes', 'Sin agua', 0)"
        )

        db.execSQL(
            "INSERT INTO tandeos (id_colonia, fecha_inicio, fecha_fin)"+
            "VALUES (1, '2026-06-10 08:00', '2026-06-10 14:00'),"+
            "(2, '2026-06-12 10:00', '2026-06-12 16:00'),"+
            "(3, '2026-06-15 07:00', '2026-06-15 13:00'),"+
            "(2, '2026-05-10 08:00', '2026-05-10 14:00'),"+
            "(1, '2026-05-05 08:00', '2026-05-05 14:00')"
        )

        db.execSQL(
            "INSERT INTO configuracion"+
            "(id_configuracion," +
            "notificaciones_tandeo," +
            "recordatorio_previo," +
            "sonido_alerta)" +
            "VALUES (1, 1, 1, 0)"
        )
    }
}