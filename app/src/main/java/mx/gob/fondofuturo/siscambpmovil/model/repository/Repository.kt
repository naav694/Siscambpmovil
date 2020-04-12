package mx.gob.fondofuturo.siscambpmovil.model.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import mx.gob.fondofuturo.siscambpmovil.model.api.RetrofitClient
import mx.gob.fondofuturo.siscambpmovil.model.data.User

object Repository {

    var job: CompletableJob? = null

    fun getUser(user: User) : LiveData<Int> {
        job = Job()
        return object : LiveData<Int>() {
            override fun onActive() {
                super.onActive()
                job.let{theJob ->
                    CoroutineScope(IO + theJob!!).launch {
                        val response = RetrofitClient.retrofitService.getUser(user.mUser!!, user.mPassword!!)
                        withContext(Main) {
                            value = response
                            theJob.complete()
                        }
                    }
                }
            }
        }
    }

    fun cancelJobs(){
        job?.cancel()
    }

}