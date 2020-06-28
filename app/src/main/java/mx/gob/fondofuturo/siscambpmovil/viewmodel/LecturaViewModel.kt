package mx.gob.fondofuturo.siscambpmovil.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import mx.gob.fondofuturo.siscambpmovil.support.interfaces.ISessionHelper
import mx.gob.fondofuturo.siscambpmovil.model.data.Lectura
import mx.gob.fondofuturo.siscambpmovil.model.repository.interfaces.ILecturaRepository
import mx.gob.fondofuturo.siscambpmovil.model.response.LecturaResponse
import mx.gob.fondofuturo.siscambpmovil.model.response.LecturaResult

class LecturaViewModel(lecturaRepository: ILecturaRepository, sessionHelper: ISessionHelper) :
    BaseViewModel(sessionHelper) {

    private val _lectura = MutableLiveData<Lectura>()
    var lectura: LiveData<LecturaResult<LecturaResponse>> = _lectura.switchMap {
        lecturaRepository.setLectura(
            "http://${sessionHelper.getBaseURL()}/lecturas_web/w_service/lecturas_service.php", it
        ).asLiveData()
    }

    fun setLectura(lectura: Lectura) {
        _lectura.value = lectura
    }

}