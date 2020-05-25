package mx.gob.fondofuturo.siscambpmovil.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mx.gob.fondofuturo.siscambpmovil.R
import mx.gob.fondofuturo.siscambpmovil.model.data.Arrendatario

class ArrendatarioAdapter(private var arrendatarioArrayList: ArrayList<Arrendatario>) :
    RecyclerView.Adapter<ArrendatarioAdapter.ArrendatarioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArrendatarioViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_arrendatario, parent, false) as View
        return ArrendatarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArrendatarioViewHolder, position: Int) {
        val arrendatario = arrendatarioArrayList[position]
    }

    override fun getItemCount(): Int = arrendatarioArrayList.size

    class ArrendatarioViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }
}