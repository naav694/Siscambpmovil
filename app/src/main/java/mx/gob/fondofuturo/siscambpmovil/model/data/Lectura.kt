package mx.gob.fondofuturo.siscambpmovil.model.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Lectura(

    @Expose
    @SerializedName("fkArrendatario")
    var fkArrendatario: Int,
    @Expose
    @SerializedName("lecturaAnt")
    var lecturaAnterior: Double,
    @Expose
    @SerializedName("lecturaAct")
    var lecturaActual: Double,
    @Expose
    @SerializedName("comment")
    var lecturaObservaciones: String,
    @Expose
    @SerializedName("user")
    var idUser: Int,
    @Expose
    @SerializedName("photo")
    var photoLectura: FotoLectura? = null
)