package mx.gob.fondofuturo.siscambpmovil.model.repository

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.RequestFuture
import mx.gob.fondofuturo.siscambpmovil.model.BaseResponse
import mx.gob.fondofuturo.siscambpmovil.model.api.VolleyClient
import mx.gob.fondofuturo.siscambpmovil.model.data.User
import org.json.JSONObject

object LoginRepository {

    fun onLogin(
        context: Context,
        user: String,
        password: String
    ): BaseResponse<User> {
        val url = "http://192.168.1.67/lecturas_web/w_service/lecturas_service.php?accion=1" +
                "&usuario=" + user +
                "&contrasena=" + password
        val request: RequestFuture<JSONObject> =
            VolleyClient.makeRequest(context, url, Request.Method.GET, JSONObject())
        val jResponse = request.get()
        val userResponse = jResponse.getJSONArray("user")
        val resultResponse = jResponse.getString("result")
        val mUser = User(
            userResponse.getJSONObject(0).getString("USUARIO"),
            "",
            userResponse.getJSONObject(0).getString("NOMBRE")
        )
        return BaseResponse(resultResponse, mUser)
    }

}