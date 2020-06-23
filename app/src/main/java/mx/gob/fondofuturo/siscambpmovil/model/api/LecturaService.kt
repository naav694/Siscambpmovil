package mx.gob.fondofuturo.siscambpmovil.model.api

import mx.gob.fondofuturo.siscambpmovil.model.data.Arrendatario
import mx.gob.fondofuturo.siscambpmovil.model.data.Lectura
import mx.gob.fondofuturo.siscambpmovil.model.data.User
import mx.gob.fondofuturo.siscambpmovil.model.response.ArrendatarioResponse
import mx.gob.fondofuturo.siscambpmovil.model.response.LecturaResponse
import mx.gob.fondofuturo.siscambpmovil.model.response.LoginResponse
import retrofit2.http.*

interface LecturaService {

    @GET
    suspend fun getArrendatarios(
        @Url baseURL: String,
        @Query("accion") accion: String,
        @Query("manzana") manzana: String
    ): ArrendatarioResponse<ArrayList<Arrendatario>>

    @Headers("Content-Type: application/json")
    @POST
    suspend fun setLectura(
        @Url baseURL: String,
        @Query("accion") accion: String,
        @Body lectura: Lectura
    ): LecturaResponse


    @GET
    suspend fun getUser(
        @Url baseURL: String,
        @Query("accion") accion: String,
        @Query("usuario") usuario: String,
        @Query("contrasena") contrasena: String
    ): LoginResponse<User>

}