package mx.gob.fondofuturo.siscambpmovil.model.data

data class Lectura(
    var fkArrendatario: Int,
    var lecturaAnterior: Double,
    var lecturaActual: Double,
    var lecturaObservaciones: String,
    var mUser: String,
    var photoLectura: FotoLectura? = null
)