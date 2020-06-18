package mx.gob.fondofuturo.siscambpmovil.model.repository

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.RequestFuture
import mx.gob.fondofuturo.siscambpmovil.model.BaseResponse
import mx.gob.fondofuturo.siscambpmovil.model.api.VolleyClient
import mx.gob.fondofuturo.siscambpmovil.model.data.Arrendatario
import mx.gob.fondofuturo.siscambpmovil.support.SharedQuery
import org.json.JSONObject

object ArrendatarioRepository {

    fun getArrendatarios(context: Context, manzana: String): BaseResponse<ArrayList<Arrendatario>> {
        val url = "http://${SharedQuery.getPrefer(context, "server")}/lecturas_web/w_service/lecturas_service.php?accion=2&manzana=$manzana"
        val request: RequestFuture<JSONObject> =
            VolleyClient.makeRequest(context, url, Request.Method.GET, JSONObject())
        val jResponse = request.get()
        val jArrayResponse = jResponse.getJSONArray("arrendatarios")
        val arrayListArrendatario: ArrayList<Arrendatario> = ArrayList()
        for (i in 0 until jArrayResponse.length()) {
            val jsonObject = jArrayResponse.getJSONObject(i)
            val arrendatario = Arrendatario()
            arrendatario.idArrendatario = jsonObject.getInt("PK_ARRENDATARIO")
            arrendatario.mArrendador = jsonObject.getString("ARRENDADOR")
            arrendatario.razonSocial = jsonObject.getString("RAZON_SOCIAL")
            arrendatario.mDomicilio = jsonObject.getString("DOMICILIO")
            arrendatario.mManzana = jsonObject.getString("MANZANA")
            arrendatario.mLote = jsonObject.getString("LOTE")
            arrendatario.lecturaAnt = jsonObject.optDouble("LECTURA_ANT")
            arrayListArrendatario.add(arrendatario)
        }
        return BaseResponse(jResponse.getString("result"), arrayListArrendatario)
    }

}