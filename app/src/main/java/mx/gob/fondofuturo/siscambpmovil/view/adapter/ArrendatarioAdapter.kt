package mx.gob.fondofuturo.siscambpmovil.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.item_arrendatario.view.*
import mx.gob.fondofuturo.siscambpmovil.R
import mx.gob.fondofuturo.siscambpmovil.model.data.Arrendatario

class ArrendatarioAdapter(
    private var arrendatarioArrayList: ArrayList<Arrendatario>,
    private val mListener: ArrendatarioAdapterListener
) :
    RecyclerView.Adapter<ArrendatarioAdapter.ArrendatarioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArrendatarioViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_arrendatario, parent, false) as View
        return ArrendatarioViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ArrendatarioViewHolder, position: Int) {
        val arrendatario = arrendatarioArrayList[position]
        holder.textIdArrendatario.text = "id Arrendatario: ${arrendatario.idArrendatario}"
        holder.textRazonSocial.text = "Razon social: ${arrendatario.razonSocial}"
        holder.textDomicilio.text = "Domicilio: ${arrendatario.mDomicilio}"
        holder.textManzana.text = "Manzana: ${arrendatario.mManzana}"
        holder.btnCapturaLectura.setOnClickListener {
            mListener.onArrendatarioClicked(arrendatario)
        }
    }

    override fun getItemCount(): Int = arrendatarioArrayList.size

    fun updateArrendatarios(arrayList: ArrayList<Arrendatario>) {
        arrendatarioArrayList = arrayList
        notifyDataSetChanged()
    }

    interface ArrendatarioAdapterListener {
        fun onArrendatarioClicked(arrendatario: Arrendatario)
    }

    class ArrendatarioViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textIdArrendatario: TextView = view.textIdArrendatario
        val textRazonSocial: TextView = view.textRazonSocial
        val textDomicilio: TextView = view.textDomicilio
        val textManzana: TextView = view.textManzana
        val btnCapturaLectura: MaterialButton = view.btnCapturaLectura
    }
}