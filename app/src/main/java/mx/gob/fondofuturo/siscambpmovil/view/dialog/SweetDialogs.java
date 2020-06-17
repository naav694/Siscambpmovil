package mx.gob.fondofuturo.siscambpmovil.view.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Telephony;

import cn.pedant.SweetAlert.SweetAlertDialog;
import mx.gob.fondofuturo.siscambpmovil.R;

/**
 * @author Edgar Rodriguez / Dinamo
 * @version 1.0, Diciembre/2018.
 */

public abstract class SweetDialogs {

    public static SweetAlertDialog sweetLoading(Context context, String text) {
        SweetAlertDialog sweetAD = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        sweetAD.setTitleText(text);
        sweetAD.getProgressHelper().setBarColor(context.getColor(R.color.colorPrimary));
        sweetAD.setCanceledOnTouchOutside(false);
        sweetAD.setCancelable(false);
        return sweetAD;
    }

    public static SweetAlertDialog sweetError(Context context, String text) {
        SweetAlertDialog sweetAD = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        sweetAD.setTitleText(text);
        sweetAD.setCanceledOnTouchOutside(false);
        sweetAD.setConfirmText("OK");
        sweetAD.setConfirmClickListener(SweetAlertDialog::dismissWithAnimation);
        return sweetAD;
    }

    public static SweetAlertDialog sweetErrorCloseActivity(Context context, String text, Activity activity) {
        SweetAlertDialog sweetAD = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        sweetAD.setTitleText(text);
        sweetAD.setCanceledOnTouchOutside(false);
        sweetAD.setConfirmText("OK");
        sweetAD.setConfirmClickListener(sweetAlertDialog -> {
            sweetAD.dismiss();
            activity.finish();
        });
        return sweetAD;
    }

    public static SweetAlertDialog sweetWarning(Context context, String text) {
        SweetAlertDialog sweetAD = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
        sweetAD.setTitleText(text);
        sweetAD.setCanceledOnTouchOutside(false);
        sweetAD.setConfirmText("OK");
        sweetAD.setConfirmClickListener(sweetAlertDialog -> sweetAD.dismissWithAnimation());
        return sweetAD;
    }

    public static SweetAlertDialog sweetWarningCloseActivity(Context context, Activity activity, String text) {
        SweetAlertDialog sweetAD = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
        sweetAD.setTitleText(text);
        sweetAD.setCanceledOnTouchOutside(false);
        sweetAD.setConfirmText("Si");
        sweetAD.setCancelText("Cancelar");
        sweetAD.setConfirmClickListener(sweetAlertDialog -> {
            sweetAD.dismiss();
            activity.finish();
        });
        sweetAD.setCancelClickListener(sweetAlertDialog -> sweetAD.dismiss());
        return sweetAD;
    }

    public static SweetAlertDialog sweetSuccessCloseActivity(Context context, String text, Activity activity) {
        SweetAlertDialog sweetAD = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
        sweetAD.setTitleText(text);
        sweetAD.setCanceledOnTouchOutside(false);
        sweetAD.setConfirmText("OK");
        sweetAD.setConfirmClickListener(sweetAlertDialog -> {
            sweetAD.dismiss();
            activity.setResult(Activity.RESULT_OK);
            activity.finish();
        });
        return sweetAD;
    }

    public static SweetAlertDialog sweetSuccess(Context context, String text) {
        SweetAlertDialog sweetAD = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
        sweetAD.setTitleText(text);
        sweetAD.setCanceledOnTouchOutside(false);
        sweetAD.setConfirmText("OK");
        sweetAD.setConfirmClickListener(sweetAlertDialog -> {
            sweetAD.dismiss();
        });
        return sweetAD;
    }

    public static SweetAlertDialog sweetWarningCloseActivity(Context context, String text, Activity activity) {
        SweetAlertDialog sweetAD = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
        sweetAD.setTitleText(text);
        sweetAD.setCanceledOnTouchOutside(false);
        sweetAD.setConfirmText("OK");
        sweetAD.setConfirmClickListener(sweetAlertDialog -> {
            sweetAD.dismiss();
            activity.finish();
        });
        return sweetAD;
    }

    public static SweetAlertDialog sweetNormalCloseMainActivity(Context context, String text, Activity activity, int pkUser) {
        SweetAlertDialog sweetAD = new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE);
        sweetAD.setTitleText(text);
        sweetAD.setCanceledOnTouchOutside(false);
        sweetAD.setConfirmText("Si");
        sweetAD.setCancelText("No");
        sweetAD.setConfirmClickListener(sweetAlertDialog -> {
            sweetAD.dismiss();
        });
        sweetAD.setCancelClickListener(SweetAlertDialog::dismiss);
        return sweetAD;
    }

    public static SweetAlertDialog sweetShareReport(Context context, Uri uri, String text) {
        SweetAlertDialog sweetAD = new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE);
        sweetAD.setTitleText("Seleccione el formato de envio");
        sweetAD.setCanceledOnTouchOutside(false);
        sweetAD.setConfirmButton("Imagen", sweetAlertDialog -> {
            sweetAD.dismiss();
            Intent intentSMS = new Intent(Intent.ACTION_SEND);
            intentSMS.putExtra(Intent.EXTRA_STREAM, uri);
            intentSMS.setType("image/jpeg");
            intentSMS.setPackage(Telephony.Sms.getDefaultSmsPackage(context));
            context.startActivity(intentSMS);
        });
        sweetAD.setCancelButton("Texto", sweetAlertDialog -> {
            sweetAD.dismiss();
            Intent intentSMS = new Intent(Intent.ACTION_SEND);
            intentSMS.putExtra(Intent.EXTRA_TEXT, text);
            intentSMS.setType("text/plain");
            intentSMS.setPackage(Telephony.Sms.getDefaultSmsPackage(context));
            context.startActivity(intentSMS);
        });
        return sweetAD;
    }

}
