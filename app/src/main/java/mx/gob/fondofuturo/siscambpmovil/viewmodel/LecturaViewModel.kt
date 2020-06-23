package mx.gob.fondofuturo.siscambpmovil.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.*
import mx.gob.fondofuturo.siscambpmovil.model.data.Lectura
import mx.gob.fondofuturo.siscambpmovil.model.repository.interfaces.ILecturaRepository
import mx.gob.fondofuturo.siscambpmovil.model.response.LecturaResponse
import mx.gob.fondofuturo.siscambpmovil.model.response.LecturaResult

class LecturaViewModel(lecturaRepository: ILecturaRepository, private val sharedPreferences: SharedPreferences) : ViewModel() {

    private val _lectura = MutableLiveData<Lectura>()
    var lectura: LiveData<LecturaResult<LecturaResponse>> = _lectura.switchMap {
        lecturaRepository.setLectura("http://${sharedPreferences.getString("server", "")}/lecturas_web/w_service/lecturas_service.php", it).asLiveData()
    }

    fun setLectura(lectura: Lectura) {
        _lectura.value = lectura
    }

}