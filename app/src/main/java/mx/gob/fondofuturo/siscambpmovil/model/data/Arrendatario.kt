package mx.gob.fondofuturo.siscambpmovil.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Arrendatario(
    var idArrendatario: Int = 0,
    var razonSocial: String = "",
    var auxContable: Int = 0,
    var mRFC: String? = null,
    var mDomicilio: String? = null,
    var mCP: String? = null,
    var mTelefono: String? = null,
    var mail: String? = null,
    var status: String? = null,
    var mObservacion: String? = null,
    var mRepresentante: String? = null,
    var idArrendador: Int = 0,
    var mArrendador: String? = null,
    var mSuperficie: Double = 0.0,
    var mLote: String? = null,
    var mManzana: String? = null,
    var tipoServicio: String? = null,
    var checkAgua: Int = 0,
    var checkMant: Int = 0,
    var checkOtros: Int = 0,
    var insUsuario: String? = null,
    var insFecha: String? = null,
    var lecturaAnt: Double = 0.0
) : Parcelable

