package mx.gob.fondofuturo.siscambpmovil.model.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import mx.gob.fondofuturo.siscambpmovil.model.api.LecturaService
import mx.gob.fondofuturo.siscambpmovil.model.data.Lectura
import mx.gob.fondofuturo.siscambpmovil.model.repository.interfaces.ILecturaRepository
import mx.gob.fondofuturo.siscambpmovil.model.response.LecturaResponse
import mx.gob.fondofuturo.siscambpmovil.model.response.LecturaResult

class LecturaRepository(private val lecturaService: LecturaService) : ILecturaRepository {

    @ExperimentalCoroutinesApi
    override fun setLectura(
        baseURL: String,
        lectura: Lectura
    ): Flow<LecturaResult<LecturaResponse>> = flow {
        emit(LecturaResult.Loading("Enviando lectura..."))
        emit(LecturaResult.Success(lecturaService.setLectura(baseURL, "3", lectura)))
    }.flowOn(Dispatchers.IO).catch {
        emit(LecturaResult.Error(it.message!!))
    }
}