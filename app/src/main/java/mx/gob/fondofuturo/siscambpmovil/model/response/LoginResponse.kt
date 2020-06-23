package mx.gob.fondofuturo.siscambpmovil.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginResponse<T>(
    @Expose
    @SerializedName("result")
    override var response: String?,
    @Expose
    @SerializedName("user")
    var user: T?
) : BaseResponse()