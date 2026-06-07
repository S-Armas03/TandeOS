package Simulacion_Repo

import android.content.Context
import com.example.tandeos.SQLiteHelper
import models.colonia

class favoritos(context: Context) {
    private val dbHelper = SQLiteHelper(context)

    fun obtenerFavoritos(): List<colonia> {
        val favoritos = mutableListOf<colonia>()
        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery("SELECT id_colonia,nombre, estado_agua, es_favorita FROM colonias WHERE es_favorita = 1", null)

        if (cursor.moveToFirst()) {
            do {
                val colonia = colonia(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id_colonia")),
                    nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                    estadoAgua = cursor.getString(cursor.getColumnIndexOrThrow("estado_agua")),
                    esFavorita = cursor.getInt(cursor.getColumnIndexOrThrow("es_favorita")) == 1
                )
                favoritos.add(colonia)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return favoritos
    }

    fun obtenerColoniasNoFavoritas(): List<colonia> {
        val colonias = mutableListOf<colonia>()
        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            """
        SELECT id_colonia, nombre, estado_agua, es_favorita
        FROM colonias
        WHERE es_favorita = 0
        ORDER BY nombre ASC
        """.trimIndent(),
            null
        )

        if (cursor.moveToFirst()) {
            do {
                val colonia = colonia(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id_colonia")),
                    nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                    estadoAgua = cursor.getString(cursor.getColumnIndexOrThrow("estado_agua")),
                    esFavorita = cursor.getInt(cursor.getColumnIndexOrThrow("es_favorita")) == 1
                )

                colonias.add(colonia)

            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return colonias
    }

    fun buscarColoniasNoFavoritas(busqueda: String): List<colonia> {
        val colonias = mutableListOf<colonia>()
        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            """
        SELECT id_colonia, nombre, estado_agua, es_favorita
        FROM colonias
        WHERE es_favorita = 0
        AND nombre LIKE ?
        ORDER BY nombre ASC
        """.trimIndent(),
            arrayOf("%$busqueda%")
        )

        if (cursor.moveToFirst()) {
            do {
                colonias.add(
                    colonia(
                        id = cursor.getInt(cursor.getColumnIndexOrThrow("id_colonia")),
                        nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                        estadoAgua = cursor.getString(cursor.getColumnIndexOrThrow("estado_agua")),
                        esFavorita = cursor.getInt(cursor.getColumnIndexOrThrow("es_favorita")) == 1
                    )
                )
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return colonias
    }

    fun agregarFavorito(idColonia: Int) {
        val db = dbHelper.writableDatabase

        db.execSQL(
            "UPDATE colonias SET es_favorita = 1 WHERE id_colonia = ?",
            arrayOf(idColonia)
        )

        db.close()
    }
}