package mx.gob.fondofuturo.siscambpmovil.model.repository.interfaces

import kotlinx.coroutines.flow.Flow
import mx.gob.fondofuturo.siscambpmovil.model.data.User
import mx.gob.fondofuturo.siscambpmovil.model.response.LecturaResult
import mx.gob.fondofuturo.siscambpmovil.model.response.LoginResponse

interface ILoginRepository {
    fun onLogin(baseURL: String, usuario: String, contrasena: String): Flow<LecturaResult<LoginResponse<User>>>
}