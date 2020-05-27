package mx.gob.fondofuturo.siscambpmovil.support

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

object SharedQuery {

    private var sharedPref: SharedPreferences? = null

    fun setPrefer(
        context: Context,
        key: String?,
        value: String
    ) {
        sharedPref = context.getSharedPreferences("SharedQuery", Context.MODE_PRIVATE)
        with (sharedPref!!.edit()) {
            putString(key, value)
            commit()
        }
    }

    fun getPrefer(
        context: Context,
        key: String?
    ): String? {
        sharedPref = context.getSharedPreferences("SharedQuery", Context.MODE_PRIVATE)
        return sharedPref!!.getString(key, "")
    }

}