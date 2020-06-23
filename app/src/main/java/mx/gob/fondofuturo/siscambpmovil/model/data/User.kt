package mx.gob.fondofuturo.siscambpmovil.model.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @Expose
    @SerializedName("PK_USUARIO")
    var idUser: Int = 0,
    @Expose
    @SerializedName("USUARIO")
    var mLogin: String = "",
    @Expose
    @SerializedName("CONTRASENA")
    var mPassword: String = "",
    @Expose
    @SerializedName("NOMBRE")
    var nameUser: String = ""
) : Parcelable