package mx.gob.fondofuturo.siscambpmovil.model.data

data class Lectura(
    var fkArrendatario: Int,
    var lecturaAnterior: Double,
    var lecturaActual: Double,
    var lecturaObservaciones: String,
    var idUser: Int,
    var photoLectura: FotoLectura? = null
)