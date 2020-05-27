package mx.gob.fondofuturo.siscambpmovil.model.repository

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.RequestFuture
import mx.gob.fondofuturo.siscambpmovil.model.api.VolleyClient
import mx.gob.fondofuturo.siscambpmovil.model.data.Lectura
import mx.gob.fondofuturo.siscambpmovil.support.SharedQuery
import org.json.JSONObject

object LecturaRepository {

    fun sendLecturaToWeb(context: Context, lectura: Lectura): String {
        val url = "http://${SharedQuery.getPrefer(context, "server")}/w_service/lecturas_service.php?accion=3"
        val jsonObject = createJSONLectura(lectura)
        val request: RequestFuture<JSONObject> =
            VolleyClient.makeRequest(context, url, Request.Method.POST, jsonObject)
        val jResponse = request.get()
        return jResponse.getString("result")
    }

    private fun createJSONLectura(lectura: Lectura): JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("fkArrendatario", lectura.fkArrendatario)
        jsonObject.put("lecturaActual", lectura.lecturaActual)
        jsonObject.put("user", lectura.mUser)
        val jsonPhoto = JSONObject()
        jsonPhoto.put("photoName", lectura.photoLectura!!.photoName)
        jsonPhoto.put("photoStr", lectura.photoLectura!!.photoLectura)
        jsonObject.put("photo", jsonPhoto)
        return jsonObject
    }
}