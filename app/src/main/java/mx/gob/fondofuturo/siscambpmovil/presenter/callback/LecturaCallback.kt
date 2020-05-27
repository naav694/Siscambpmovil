package mx.gob.fondofuturo.siscambpmovil.presenter.callback

interface LecturaCallback {
    fun onSuccess()
    fun onLoading(message: String)
    fun onError(message: String)
}