package mx.gob.fondofuturo.siscambpmovil.presenter.callback

import mx.gob.fondofuturo.siscambpmovil.model.data.User

interface LoginCallback {
    fun onLoading(message: String)
    fun onSuccess(user: User)
    fun onError(message: String)
}