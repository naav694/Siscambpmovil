package mx.gob.fondofuturo.siscambpmovil.view.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.text.InputType
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.layout_arrendatario_filter_dialog.view.*
import mx.gob.fondofuturo.siscambpmovil.R

object ServerConfigDialog : DialogFragment() {

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = ArrendatarioFilterDialog.requireActivity().layoutInflater
            val dialogView = inflater.inflate(R.layout.dialog_server_config, null)
            builder.setView(dialogView)
                .setMessage("Escriba el nombre del servidor o IP")
                .setPositiveButton("Guardar") { dialog, _ ->
                    dialogView.
                    dialog.dismiss()
                }
                .setNegativeButton("Cerrar") { dialog, _ ->
                    dialog.dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}