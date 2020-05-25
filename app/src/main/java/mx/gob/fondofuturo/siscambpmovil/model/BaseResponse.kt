package mx.gob.fondofuturo.siscambpmovil.model

data class BaseResponse<T>(
    var response: String? = null,
    var data: T
)

