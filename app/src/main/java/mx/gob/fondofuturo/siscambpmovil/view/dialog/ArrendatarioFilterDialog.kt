package mx.gob.fondofuturo.siscambpmovil.view.dialog

import android.annotation.SuppressLint

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.layout_arrendatario_filter_dialog.view.*
import mx.gob.fondofuturo.siscambpmovil.R

object ArrendatarioFilterDialog : DialogFragment() {

    private lateinit var mListener: ArrendatarioFilterDialogListener

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = ArrendatarioFilterDialog.requireActivity().layoutInflater
            val dialogView = inflater.inflate(R.layout.layout_arrendatario_filter_dialog, null)
            val adapter = ArrayAdapter.createFromResource(context!!, R.array.array_manzana, R.layout.support_simple_spinner_dropdown_item)
            dialogView.dropdownManzanaFilter.inputType = InputType.TYPE_NULL
            dialogView.dropdownManzanaFilter.setAdapter(adapter)
            builder.setView(dialogView)
                .setMessage("Seleccione una manzana")
                .setPositiveButton("Filtrar") { dialog, _ ->
                    mListener.onFilterClicked(dialogView.dropdownManzanaFilter.text.toString())
                    dialog.dismiss()
                }
                .setNegativeButton("Cerrar") { dialog, _ ->
                    dialog.dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mListener = context as ArrendatarioFilterDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                (context.toString() +
                        " must implement ArrendatarioFilterDialogListener")
            )
        }
    }

    interface ArrendatarioFilterDialogListener {
        fun onFilterClicked(manzana: String)
    }

}