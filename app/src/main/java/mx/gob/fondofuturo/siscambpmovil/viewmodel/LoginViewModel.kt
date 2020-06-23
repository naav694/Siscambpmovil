package mx.gob.fondofuturo.siscambpmovil.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import mx.gob.fondofuturo.siscambpmovil.model.data.User
import mx.gob.fondofuturo.siscambpmovil.model.repository.interfaces.ILoginRepository
import mx.gob.fondofuturo.siscambpmovil.model.response.LecturaResult
import mx.gob.fondofuturo.siscambpmovil.model.response.LoginResponse

class LoginViewModel(
    private val loginRepository: ILoginRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    var usuario: String = ""
    var contrasena: String = ""
    var rememberMe: Boolean = false

    private val _onLogin = MutableLiveData<LecturaResult<LoginResponse<User>>>()
    var onLogin: LiveData<LecturaResult<LoginResponse<User>>> = _onLogin

    private val _onToast = MutableLiveData<Boolean>()
    var onToast: LiveData<Boolean> = _onToast

    init {
        if (sharedPreferences.getString("server", "").isNullOrEmpty()) {
            _onToast.value = true
        } else {
            val rememberedUser = sharedPreferences.getString("usuario", "")
            val rememberedPass = sharedPreferences.getString("contrasena", "")
            if (!rememberedUser.isNullOrEmpty() && !rememberedPass.isNullOrEmpty()) {
                onLoginFlow(rememberedUser, rememberedPass)
            }
        }
    }

    fun onLogin() {
        if (sharedPreferences.getString("server", "").isNullOrEmpty()) {
            _onToast.value = true
        } else {
            onLoginFlow(usuario, contrasena)
        }
    }

    private fun onLoginFlow(usuario: String, contrasena: String) {
        viewModelScope.launch {
            loginRepository.onLogin(
                "http://${sharedPreferences.getString(
                    "server",
                    ""
                )}/lecturas_web/w_service/lecturas_service.php",
                usuario,
                contrasena
            ).collect {
                if (rememberMe) {
                    when (it) {
                        is LecturaResult.Success -> {
                            with(sharedPreferences.edit()) {
                                putString("usuario", usuario)
                                putString("contrasena", contrasena)
                                commit()
                            }
                        }
                    }
                }
                _onLogin.value = it
            }
        }
    }

}