package mx.gob.fondofuturo.siscambpmovil.model.repository.implementation

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import mx.gob.fondofuturo.siscambpmovil.model.api.LecturaService
import mx.gob.fondofuturo.siscambpmovil.model.data.User
import mx.gob.fondofuturo.siscambpmovil.model.repository.interfaces.ILoginRepository
import mx.gob.fondofuturo.siscambpmovil.model.response.LecturaResult
import mx.gob.fondofuturo.siscambpmovil.model.response.LoginResponse

class LoginRepository(private val lecturaService: LecturaService) : ILoginRepository {

    @ExperimentalCoroutinesApi
    override fun onLogin(
        baseURL: String,
        usuario: String,
        contrasena: String
    ): Flow<LecturaResult<LoginResponse<User>>> = flow {
        emit(LecturaResult.Loading("Iniciando sesión..."))
        emit(LecturaResult.Success(lecturaService.getUser(baseURL, "1", usuario, contrasena)))
    }.flowOn(Dispatchers.IO).catch {
        emit(LecturaResult.Error(it.message!!))
    }

}