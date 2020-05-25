package mx.gob.fondofuturo.siscambpmovil.view.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.layout_arrendatario_filter_dialog.view.*
import mx.gob.fondofuturo.siscambpmovil.R

object ArrendatarioFilterDialog : DialogFragment() {

    private lateinit var mListener: ArrendatarioFilterDialogListener

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val dialogView = inflater.inflate(R.layout.layout_arrendatario_filter_dialog, null)
            builder.setView(dialogView)
                .setMessage("Seleccione una manzana")
                .setPositiveButton("Filtrar") { dialog, _ ->
                    mListener.onFilterClicked(dialogView.dropdownArrendatarioFilter.text.toString())
                    dialog.dismiss()
                }
                .setNegativeButton("Cerrar") { dialog, _ ->
                    dialog.dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    interface ArrendatarioFilterDialogListener {
        fun onFilterClicked(manzana: String)
    }

}