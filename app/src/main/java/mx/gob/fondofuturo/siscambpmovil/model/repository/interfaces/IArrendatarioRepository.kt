package mx.gob.fondofuturo.siscambpmovil.model.repository.interfaces

import kotlinx.coroutines.flow.Flow
import mx.gob.fondofuturo.siscambpmovil.model.data.Arrendatario
import mx.gob.fondofuturo.siscambpmovil.model.response.ArrendatarioResponse
import mx.gob.fondofuturo.siscambpmovil.model.response.LecturaResult

interface IArrendatarioRepository {
    fun getArrendatarios(baseURL: String, manzana: String): Flow<LecturaResult<ArrendatarioResponse<ArrayList<Arrendatario>>>>
}