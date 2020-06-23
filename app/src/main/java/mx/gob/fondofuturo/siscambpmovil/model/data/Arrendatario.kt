package mx.gob.fondofuturo.siscambpmovil.model.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Arrendatario(
    @Expose
    @SerializedName("PK_ARRENDATARIO")
    var idArrendatario: Int = 0,
    @Expose
    @SerializedName("RAZON_SOCIAL")
    var razonSocial: String = "",
    var auxContable: Int = 0,
    var mRFC: String? = null,
    @Expose
    @SerializedName("DOMICILIO")
    var mDomicilio: String? = null,
    var mCP: String? = null,
    var mTelefono: String? = null,
    var mail: String? = null,
    var status: String? = null,
    var mObservacion: String? = null,
    var mRepresentante: String? = null,
    var idArrendador: Int = 0,
    @Expose
    @SerializedName("ARRENDADOR")
    var mArrendador: String? = null,
    var mSuperficie: Double = 0.0,
    @Expose
    @SerializedName("LOTE")
    var mLote: String? = null,
    @Expose
    @SerializedName("MANZANA")
    var mManzana: String? = null,
    var tipoServicio: String? = null,
    var checkAgua: Int = 0,
    var checkMant: Int = 0,
    var checkOtros: Int = 0,
    var insUsuario: String? = null,
    var insFecha: String? = null,
    @Expose
    @SerializedName("LECTURA_ANT")
    var lecturaAnt: Double = 0.0
) : Parcelable

