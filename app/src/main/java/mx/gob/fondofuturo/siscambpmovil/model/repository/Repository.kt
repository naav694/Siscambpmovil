package mx.gob.fondofuturo.siscambpmovil.model.repository

import android.util.MalformedJsonException
import androidx.lifecycle.LiveData
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import mx.gob.fondofuturo.siscambpmovil.model.api.RetrofitClient
import mx.gob.fondofuturo.siscambpmovil.model.data.User
import org.json.JSONObject

object Repository {

    var job: CompletableJob? = null

    fun getUser(user: User): LiveData<String> {
        job = Job()
        return object : LiveData<String>() {
            override fun onActive() {
                super.onActive()
                job.let { theJob ->
                    CoroutineScope(IO + theJob!!).launch {
                        val response =
                            RetrofitClient.retrofitService.getUser(user.mUser!!, user.mPassword!!)
                        withContext(Main) {
                            value = response
                            theJob.complete()
                        }
                    }
                }
            }
        }
    }

    fun cancelJobs() {
        job?.cancel()
    }

}