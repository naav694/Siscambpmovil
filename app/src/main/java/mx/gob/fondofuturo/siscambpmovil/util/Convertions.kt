package mx.gob.fondofuturo.siscambpmovil.util

import android.util.Base64

object Convertions {
    fun convertByteToString(photo: ByteArray?): String {
        return Base64.encodeToString(photo, Base64.DEFAULT)
    }

    fun convertStringToByte(photo: String?): ByteArray {
        return Base64.decode(photo, Base64.DEFAULT)
    }
}