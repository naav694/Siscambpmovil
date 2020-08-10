package mx.gob.fondofuturo.siscambpmovil.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import mx.gob.fondofuturo.siscambpmovil.model.data.Arrendatario
import mx.gob.fondofuturo.siscambpmovil.model.repository.interfaces.IArrendatarioRepository
import mx.gob.fondofuturo.siscambpmovil.model.response.ArrendatarioResponse
import mx.gob.fondofuturo.siscambpmovil.model.response.LecturaResult
import mx.gob.fondofuturo.siscambpmovil.util.interfaces.ISessionHelper

class ArrendatarioViewModel(
    private val arrendatarioRepository: IArrendatarioRepository, sessionHelper: ISessionHelper
) :
    BaseViewModel(sessionHelper) {

    private val _manzana = MutableLiveData<String>()

    var arrendatario: LiveData<LecturaResult<ArrendatarioResponse<ArrayList<Arrendatario>>>> =
        _manzana.switchMap {
            arrendatarioRepository.getArrendatarios(
                "http://${sessionHelper.getBaseURL()}/lecturas_web/w_service/lecturas_service.php",
                it
            ).asLiveData()
        }

    fun setManzana(manzana: String) {
        _manzana.value = manzana
    }

}