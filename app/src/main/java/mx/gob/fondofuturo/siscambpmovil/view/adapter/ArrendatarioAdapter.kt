package mx.gob.fondofuturo.siscambpmovil.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.item_arrendatario.view.*
import mx.gob.fondofuturo.siscambpmovil.R
import mx.gob.fondofuturo.siscambpmovil.model.data.Arrendatario
import java.util.*
import kotlin.collections.ArrayList

class ArrendatarioAdapter(
    private var arrendatarioArrayList: ArrayList<Arrendatario>,
    private val mListener: ArrendatarioAdapterListener
) :
    RecyclerView.Adapter<ArrendatarioAdapter.ArrendatarioViewHolder>(),
    Filterable {

    private var filterArrayList: ArrayList<Arrendatario> = ArrayList()
    private var year: Int = 0
    private var month: Int = 0

    init {
        val cal = Calendar.getInstance(Locale.getDefault())
        cal.time = Date()
        month = cal[Calendar.MONTH] + 1
        year = cal[Calendar.YEAR]
        filterArrayList = arrendatarioArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArrendatarioViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_arrendatario, parent, false) as View
        return ArrendatarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArrendatarioViewHolder, position: Int) {
        val arrendatario = filterArrayList[position]
        holder.textArrendador.text = arrendatario.mArrendador
        holder.textArrendatario.text = arrendatario.razonSocial
        holder.textDomicilio.text = arrendatario.mDomicilio
        holder.textManzana.text = arrendatario.mManzana
        if (month == arrendatario.mesLectura && year == arrendatario.anioLectura) {
            holder.btnCapturaLectura.visibility = View.GONE
            Glide.with(holder.itemView).load(R.drawable.status_48_green).into(holder.imageStatus)
        } else {
            holder.btnCapturaLectura.visibility = View.VISIBLE
            Glide.with(holder.itemView).load(R.drawable.status_48_red).into(holder.imageStatus)
        }
        holder.btnCapturaLectura.setOnClickListener {
            mListener.onArrendatarioClicked(arrendatario)
        }
    }

    override fun getItemCount(): Int = filterArrayList.size

    fun updateArrendatarios(arrayList: ArrayList<Arrendatario>) {
        filterArrayList = arrayList
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                val resultList = ArrayList<Arrendatario>()
                filterArrayList = if (charSearch.isNotEmpty()) {
                    for (a in arrendatarioArrayList) {
                        if (a.razonSocial.contains(
                                charSearch.toUpperCase(
                                    Locale.getDefault()
                                )
                            )
                        ) {
                            resultList.add(a)
                        }
                    }
                    resultList
                } else {
                    arrendatarioArrayList
                }
                val filterResults = FilterResults()
                filterResults.values = resultList
                filterResults.count = resultList.size
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null && results.count > 0) {
                    filterArrayList = results.values as ArrayList<Arrendatario>
                    notifyDataSetChanged()
                }
            }
        }
    }

    interface ArrendatarioAdapterListener {
        fun onArrendatarioClicked(arrendatario: Arrendatario)
    }

    class ArrendatarioViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textArrendador: TextView = view.textArrendador
        val textArrendatario: TextView = view.textArrendatario
        val textDomicilio: TextView = view.textDomicilio
        val textManzana: TextView = view.textManzana
        val btnCapturaLectura: MaterialButton = view.btnCapturaLectura
        val imageStatus: ImageView = view.imageStatus
    }
}