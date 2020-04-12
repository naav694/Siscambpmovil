package mx.gob.fondofuturo.siscambpmovil.model.api

import org.json.JSONObject
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("lecturas_service.php?accion=1")
    suspend fun getUser(
        @Query("usuario") user: String,
        @Query("contrasena") password: String
    ) : String
}