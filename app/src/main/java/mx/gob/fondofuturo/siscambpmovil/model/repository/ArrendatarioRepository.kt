package mx.gob.fondofuturo.siscambpmovil.model.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import mx.gob.fondofuturo.siscambpmovil.model.api.LecturaService
import mx.gob.fondofuturo.siscambpmovil.model.data.Arrendatario
import mx.gob.fondofuturo.siscambpmovil.model.repository.interfaces.IArrendatarioRepository
import mx.gob.fondofuturo.siscambpmovil.model.response.ArrendatarioResponse
import mx.gob.fondofuturo.siscambpmovil.model.response.LecturaResult

class ArrendatarioRepository(private val lecturaService: LecturaService) : IArrendatarioRepository {
    @ExperimentalCoroutinesApi
    override fun getArrendatarios(
        baseURL: String,
        manzana: String
    ): Flow<LecturaResult<ArrendatarioResponse<ArrayList<Arrendatario>>>> =
        flow {
            emit(LecturaResult.Loading("Obteniendo Arrendatarios..."))
            emit(LecturaResult.Success(lecturaService.getArrendatarios(baseURL, "2", manzana)))
        }.flowOn(Dispatchers.IO).catch {
            emit(LecturaResult.Error(it.message!!))
        }
}