package mx.gob.fondofuturo.siscambpmovil.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.*
import mx.gob.fondofuturo.siscambpmovil.model.data.Arrendatario
import mx.gob.fondofuturo.siscambpmovil.model.repository.interfaces.IArrendatarioRepository
import mx.gob.fondofuturo.siscambpmovil.model.response.ArrendatarioResponse
import mx.gob.fondofuturo.siscambpmovil.model.response.LecturaResult

class ArrendatarioViewModel(
    private val arrendatarioRepository: IArrendatarioRepository,
    private val sharedPreferences: SharedPreferences
) :
    ViewModel() {

    private val _manzana = MutableLiveData<String>()

    var arrendatario: LiveData<LecturaResult<ArrendatarioResponse<ArrayList<Arrendatario>>>> =
        _manzana.switchMap {
            arrendatarioRepository.getArrendatarios(
                "http://${sharedPreferences.getString("server", "")}/lecturas_web/w_service/lecturas_service.php",
                it
            ).asLiveData()
        }

    fun setManzana(manzana: String) {
        _manzana.value = manzana
    }

}