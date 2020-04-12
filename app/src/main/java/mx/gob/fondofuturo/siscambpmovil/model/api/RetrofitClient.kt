package mx.gob.fondofuturo.siscambpmovil.model.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


object RetrofitClient {

    private const val BASE_URL = "http://192.168.1.71/lecturas_web/w_service/"

    private val retrofitBuilder: Retrofit.Builder by lazy {

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val gson: Gson = GsonBuilder()
            .serializeNulls()
            .serializeSpecialFloatingPointValues()
            .setLenient()
            .create()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    val retrofitService: RetrofitService by lazy {
        retrofitBuilder
            .build()
            .create(RetrofitService::class.java)
    }
}