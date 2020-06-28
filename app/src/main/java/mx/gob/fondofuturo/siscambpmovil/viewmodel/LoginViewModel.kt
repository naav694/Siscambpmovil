package mx.gob.fondofuturo.siscambpmovil.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import mx.gob.fondofuturo.siscambpmovil.support.interfaces.ISessionHelper
import mx.gob.fondofuturo.siscambpmovil.model.data.User
import mx.gob.fondofuturo.siscambpmovil.model.repository.interfaces.ILoginRepository
import mx.gob.fondofuturo.siscambpmovil.model.response.LecturaResult
import mx.gob.fondofuturo.siscambpmovil.model.response.LoginResponse

class LoginViewModel(
    private val loginRepository: ILoginRepository, sessionHelper: ISessionHelper
) : BaseViewModel(sessionHelper) {

    var usuario: String = ""
    var contrasena: String = ""
    var rememberMe: Boolean = false

    private val _onLogin = MutableLiveData<LecturaResult<LoginResponse<User>>>()
    var onLogin: LiveData<LecturaResult<LoginResponse<User>>> = _onLogin

    private val _onToast = MutableLiveData<Boolean>()
    var onToast: LiveData<Boolean> = _onToast

    init {
        if (sessionHelper.getBaseURL().isEmpty()) {
            _onToast.value = true
        } else {
            if (sessionHelper.getRememberSession()) {
                val user = sessionHelper.getUserSession()
                onLoginFlow(user.mLogin, user.mPassword)
            }
        }
    }

    fun onLogin() {
        if (sessionHelper.getBaseURL().isEmpty()) {
            _onToast.value = true
        } else {
            onLoginFlow(usuario, contrasena)
        }
    }

    private fun onLoginFlow(usuario: String, contrasena: String) {
        viewModelScope.launch {
            loginRepository.onLogin(
                "http://${sessionHelper.getBaseURL()}/lecturas_web/w_service/lecturas_service.php",
                usuario,
                contrasena
            ).collect {
                when (it) {
                    is LecturaResult.Success -> {
                        val user = it.data.user
                        user!!.mPassword = contrasena
                        sessionHelper.setUserSession(user)
                        if (rememberMe) {
                            sessionHelper.setRememberSession(rememberMe)
                        }
                    }
                }
                _onLogin.value = it
            }
        }
    }

}