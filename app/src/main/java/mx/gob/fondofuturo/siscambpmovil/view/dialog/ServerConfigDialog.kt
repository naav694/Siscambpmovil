package mx.gob.fondofuturo.siscambpmovil.view.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_server_config.view.*
import mx.gob.fondofuturo.siscambpmovil.R
import mx.gob.fondofuturo.siscambpmovil.util.interfaces.ISessionHelper
import org.koin.android.ext.android.inject

object ServerConfigDialog : DialogFragment() {

    private val sessionHelper: ISessionHelper by inject()

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = ServerConfigDialog.requireActivity().layoutInflater
            val dialogView = inflater.inflate(R.layout.dialog_server_config, null)
            dialogView.editServerConfig.setText(sessionHelper.getBaseURL())
            builder.setView(dialogView)
                .setMessage("Escriba el nombre del servidor o IP")
                .setPositiveButton("Guardar") { dialog, _ ->
                    sessionHelper.setBaseURL(dialogView.editServerConfig.text.toString())
                    dialog.dismiss()
                }
                .setNegativeButton("Cerrar") { dialog, _ ->
                    dialog.dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}