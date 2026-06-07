package Simulacion_Repo

import android.content.Context
import com.example.tandeos.SQLiteHelper
import models.Configuracion

class configDB(context: Context) {

    private val dbHelper = SQLiteHelper(context)

    fun obtenerConfiguracion(): Configuracion {
        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            """
            SELECT notificaciones_tandeo, recordatorio_previo, sonido_alerta
            FROM configuracion
            WHERE id_configuracion = 1
            """.trimIndent(),
            null
        )

        var configuracion = Configuracion(
            notificacionesTandeo = false,
            recordatorioPrevio = false,
            sonidoAlerta = false
        )

        if (cursor.moveToFirst()) {
            configuracion = Configuracion(
                notificacionesTandeo = cursor.getInt(cursor.getColumnIndexOrThrow("notificaciones_tandeo")) == 1,
                recordatorioPrevio = cursor.getInt(cursor.getColumnIndexOrThrow("recordatorio_previo")) == 1,
                sonidoAlerta = cursor.getInt(cursor.getColumnIndexOrThrow("sonido_alerta")) == 1
            )
        }

        cursor.close()
        db.close()

        return configuracion
    }

    fun actualizarNotificacionesTandeo(activado: Boolean) {
        val db = dbHelper.writableDatabase
        val valor = if (activado) 1 else 0

        db.execSQL(
            """
            UPDATE configuracion
            SET notificaciones_tandeo = ?
            WHERE id_configuracion = 1
            """.trimIndent(),
            arrayOf(valor)
        )

        db.close()
    }

    fun actualizarRecordatorioPrevio(activado: Boolean) {
        val db = dbHelper.writableDatabase
        val valor = if (activado) 1 else 0

        db.execSQL(
            """
            UPDATE configuracion
            SET recordatorio_previo = ?
            WHERE id_configuracion = 1
            """.trimIndent(),
            arrayOf(valor)
        )

        db.close()
    }

    fun actualizarSonidoAlerta(activado: Boolean) {
        val db = dbHelper.writableDatabase
        val valor = if (activado) 1 else 0

        db.execSQL(
            """
            UPDATE configuracion
            SET sonido_alerta = ?
            WHERE id_configuracion = 1
            """.trimIndent(),
            arrayOf(valor)
        )

        db.close()
    }
}