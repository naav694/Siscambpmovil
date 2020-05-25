package mx.gob.fondofuturo.siscambpmovil.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var mUser: String = "",
    var mPassword: String = "",
    var nameUser: String = ""
) : Parcelable