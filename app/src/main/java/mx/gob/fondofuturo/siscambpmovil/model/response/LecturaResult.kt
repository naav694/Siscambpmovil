package mx.gob.fondofuturo.siscambpmovil.model.response

sealed class LecturaResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : LecturaResult<T>()
    data class Error(val error: String) : LecturaResult<Nothing>()
    data class Loading(val message: String) : LecturaResult<Nothing>()
}