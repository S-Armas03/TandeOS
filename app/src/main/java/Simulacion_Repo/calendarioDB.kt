package Simulacion_Repo

import android.content.Context
import com.example.tandeos.SQLiteHelper
import models.Tandeo

class calendarioDB(context: Context) {

    private val dbHelper = SQLiteHelper(context)

    fun obtenerTandeos(): List<Tandeo> {
        val tandeos = mutableListOf<Tandeo>()
        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            """
        SELECT 
            t.id_tandeo,
            c.nombre AS nombre_colonia,
            t.fecha_inicio,
            t.fecha_fin
        FROM tandeos t
        INNER JOIN colonias c 
            ON t.id_colonia = c.id_colonia
        WHERE t.fecha_fin >= datetime('now', 'localtime')
        AND c.es_favorita = 1
        ORDER BY t.fecha_inicio ASC
        """.trimIndent(),
            null
        )

        if (cursor.moveToFirst()) {
            do {
                val tandeo = Tandeo(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id_tandeo")),
                    nombreColonia = cursor.getString(cursor.getColumnIndexOrThrow("nombre_colonia")),
                    fechaInicio = cursor.getString(cursor.getColumnIndexOrThrow("fecha_inicio")),
                    fechaFin = cursor.getString(cursor.getColumnIndexOrThrow("fecha_fin"))
                )
                tandeos.add(tandeo)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return tandeos
    }

    fun obtenerHistorial(): List<Tandeo> {
        val tandeos = mutableListOf<Tandeo>()
        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            """
        SELECT 
            t.id_tandeo,
            c.nombre AS nombre_colonia,
            t.fecha_inicio,
            t.fecha_fin
        FROM tandeos t
        INNER JOIN colonias c 
            ON t.id_colonia = c.id_colonia
        WHERE t.fecha_fin < datetime('now', 'localtime')
        AND c.es_favorita = 1
        ORDER BY t.fecha_inicio DESC
        """.trimIndent(),
            null
        )
        if (cursor.moveToFirst()) {
            do {
                val tandeo = Tandeo(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id_tandeo")),
                    nombreColonia = cursor.getString(cursor.getColumnIndexOrThrow("nombre_colonia")),
                    fechaInicio = cursor.getString(cursor.getColumnIndexOrThrow("fecha_inicio")),
                    fechaFin = cursor.getString(cursor.getColumnIndexOrThrow("fecha_fin"))
                )

                tandeos.add(tandeo)

            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return tandeos
    }
}