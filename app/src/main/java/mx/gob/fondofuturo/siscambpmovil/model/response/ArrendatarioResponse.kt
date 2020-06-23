package mx.gob.fondofuturo.siscambpmovil.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ArrendatarioResponse<T>(
    @Expose
    @SerializedName("result")
    override var response: String?,
    @Expose
    @SerializedName("arrendatarios")
    var data: T?
) : BaseResponse()