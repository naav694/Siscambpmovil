package mx.gob.fondofuturo.siscambpmovil.model.repository.interfaces

import kotlinx.coroutines.flow.Flow
import mx.gob.fondofuturo.siscambpmovil.model.data.Lectura
import mx.gob.fondofuturo.siscambpmovil.model.response.LecturaResponse
import mx.gob.fondofuturo.siscambpmovil.model.response.LecturaResult

interface ILecturaRepository {
    fun setLectura(baseURL: String, lectura: Lectura): Flow<LecturaResult<LecturaResponse>>
}