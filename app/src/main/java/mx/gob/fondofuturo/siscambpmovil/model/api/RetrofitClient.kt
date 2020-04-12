package mx.gob.fondofuturo.siscambpmovil.model.api

import mx.gob.fondofuturo.siscambpmovil.s.RetrofitService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "/lecturas_web/w_service/lecturas_service.php?"

    private val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val retrofitService : RetrofitService by lazy {
        retrofitBuilder
            .build()
            .create(RetrofitService::class.java)
    }
}