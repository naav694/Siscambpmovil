package mx.gob.fondofuturo.siscambpmovil.s

import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {

    @GET("action=1&usuario={user}&contrasena={contrasena}")
    suspend fun getUser(
        @Path("user") user: String,
        @Path("contrasena") password: String
    ) : Int
}