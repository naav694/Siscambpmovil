package mx.gob.fondofuturo.siscambpmovil

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import mx.gob.fondofuturo.siscambpmovil.model.api.LecturaService
import mx.gob.fondofuturo.siscambpmovil.model.repository.ArrendatarioRepository
import mx.gob.fondofuturo.siscambpmovil.model.repository.LecturaRepository
import mx.gob.fondofuturo.siscambpmovil.model.repository.LoginRepository
import mx.gob.fondofuturo.siscambpmovil.model.repository.interfaces.IArrendatarioRepository
import mx.gob.fondofuturo.siscambpmovil.model.repository.interfaces.ILecturaRepository
import mx.gob.fondofuturo.siscambpmovil.model.repository.interfaces.ILoginRepository
import mx.gob.fondofuturo.siscambpmovil.viewmodel.ArrendatarioViewModel
import mx.gob.fondofuturo.siscambpmovil.viewmodel.LecturaViewModel
import mx.gob.fondofuturo.siscambpmovil.viewmodel.LoginViewModel
import mx.gob.fondofuturo.siscambpmovil.viewmodel.SplashViewModel
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(retrofitModule, repositoryModule, viewModelModule, sharedPreferencesModule)
        }
    }

    private val retrofitModule = module {
        single {
            val cacheSize: Long = 10 * 1024 * 1024 // 10mb
            val mCache = Cache(cacheDir, cacheSize)
            OkHttpClient().newBuilder()
                .cache(mCache)
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level =
                        HttpLoggingInterceptor.Level.BODY
                })
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()
        }

        single { Gson() }

        single {
            Retrofit.Builder()
                .baseUrl("http://fondofuturo.gob.mx/")
                .client(get())
                .addConverterFactory(GsonConverterFactory.create(get()))
                .build()
        }

        single { get<Retrofit>().create(LecturaService::class.java) }
    }

    private val repositoryModule = module {
        factory<IArrendatarioRepository> { ArrendatarioRepository(get()) }
        factory<ILecturaRepository> { LecturaRepository(get()) }
        factory<ILoginRepository> { LoginRepository(get()) }
    }

    private val viewModelModule = module {
        viewModel { SplashViewModel(get()) }
        viewModel { LoginViewModel(get(), get()) }
        viewModel { ArrendatarioViewModel(get(), get()) }
        viewModel { LecturaViewModel(get(), get()) }
    }

    private val sharedPreferencesModule = module {
        single<SharedPreferences> {
            androidContext().getSharedPreferences("SharedLectura", Context.MODE_PRIVATE)
        }
    }


}