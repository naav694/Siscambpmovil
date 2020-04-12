package mx.gob.fondofuturo.siscambpmovil.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import mx.gob.fondofuturo.siscambpmovil.model.data.User
import mx.gob.fondofuturo.siscambpmovil.model.repository.Repository
import org.json.JSONObject

class LoginViewModel : ViewModel() {

    private val mUser: MutableLiveData<User> = MutableLiveData()

    val response: LiveData<String> = Transformations
        .switchMap(mUser) {
            Repository.getUser(it)
        }

    fun setUserPassword(user: User) {
        val update1: User = user
        if(mUser.value == update1) {
            return
        }
        mUser.value = user
    }

    fun cancelJobs(){
        Repository.cancelJobs()
    }
}