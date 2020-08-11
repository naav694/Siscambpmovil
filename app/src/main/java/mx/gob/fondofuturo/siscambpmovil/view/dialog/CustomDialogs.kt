package mx.gob.fondofuturo.siscambpmovil.view.dialog

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import cn.pedant.SweetAlert.SweetAlertDialog

object CustomDialogs {
    fun sweetSuccessCloseActivity(context: Context, text: String, activity: Activity) {
        return SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
            .setTitleText(text)
            .setConfirmText("OK")
            .setConfirmClickListener {
                it.dismiss()
                activity.setResult(RESULT_OK)
                activity.finish()
            }
            .show()
    }

    fun sweetSuccess(context: Context, text: String) {
        return SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
            .setTitleText(text)
            .setConfirmText("OK")
            .setConfirmClickListener {
                it.dismiss()
            }
            .show()
    }

    fun sweetError(context: Context, text: String) {
        return SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
            .setTitleText(text)
            .setConfirmText("OK")
            .setConfirmClickListener {
                it.dismiss()
            }
            .show()
    }

    fun sweetWarning(context: Context, text: String) {
        return SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
            .setTitleText(text)
            .setConfirmText("OK")
            .setConfirmClickListener {
                it.dismiss()
            }
            .show()
    }

    fun sweetLoading(context: Context, text: String): SweetAlertDialog {
        return SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
            .setTitleText(text)
    }
}