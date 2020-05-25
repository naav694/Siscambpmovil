package mx.gob.fondofuturo.siscambpmovil.model.api

import android.content.Context
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.Volley
import org.json.JSONObject

object VolleyClient {

    fun makeRequest(
        context: Context?,
        url: String?,
        tipo: Int,
        json: JSONObject?
    ): RequestFuture<JSONObject> {
        val requestQueue = Volley.newRequestQueue(context)
        val future = RequestFuture.newFuture<JSONObject>()
        val request = JsonObjectRequest(
            tipo,
            url, json, future, future
        )
        request.retryPolicy = DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        requestQueue.add(request)
        return future
    }


}