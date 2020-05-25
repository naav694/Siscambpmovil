package mx.gob.fondofuturo.siscambpmovil.presenter.callback

import mx.gob.fondofuturo.siscambpmovil.model.data.Arrendatario

interface ArrendatarioCallback {
    fun onLoading(message: String)
    fun onSuccess(arrayListArrendatario: ArrayList<Arrendatario>)
    fun onError(message: String)
}