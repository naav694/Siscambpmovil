package mx.gob.fondofuturo.siscambpmovil.model.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FotoLectura(
    @Expose
    @SerializedName("photoStr")
    var photoLectura: String = "",
    @Expose
    @SerializedName("photoName")
    var photoName: String = "",
    var photoMobilePath: String = ""
)