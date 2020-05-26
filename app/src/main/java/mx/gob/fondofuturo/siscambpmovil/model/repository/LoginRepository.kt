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
        val url = "http://192.168.1.67/w_service/lecturas_service.php?accion=1" +
                "&usuario=" + user +
                "&contrasena=" + password
        val request: RequestFuture<JSONObject> =
            VolleyClient.makeRequest(context, url, Request.Method.GET, JSONObject())
        val jResponse = request.get()
        val resultResponse = jResponse.getString("result")
        return if (resultResponse.isEmpty()) {
            val userResponse = jResponse.getJSONObject("user")
            val mUser = User(
                userResponse.getString("USUARIO"),
                "",
                userResponse.getString("NOMBRE")
            )
            BaseResponse(resultResponse, mUser)
        } else {
            BaseResponse(resultResponse, User())
        }
    }

}