package com.app.aquahey.purepani.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.aquahey.purepani.R;

/**
 * Created by Ikram on 11/8/2017.
 */

public class ErrorUtils {
    public static final String TAG = "AquaHey";
    public static boolean emptyCheck(final EditText editText) {
        if (TextUtils.isEmpty(editText.getText().toString())) {
            editText.setError("This field is required");
            editText.requestFocus();
            return true;
        } else {
            return false;
        }
    }

    public static boolean lengthCheck(final EditText editText, final int maxNumber) {
        if (editText.getText().toString().length() < maxNumber) {
            editText.setError("Invalid Number");
            editText.requestFocus();
            return true;
        } else {
            return false;
        }
    }

    public static boolean emailValidator(String email) {
        return TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    public static boolean isOnline(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            Toast.makeText(context, "Please Check your Interner Connection", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public static void showToast(Context context, String message) {
        if (context != null) {
            final Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    public static void DEBUG(String sb) {
        if (sb.length() > 4000) {
            int chunkCount = sb.length() / 4000;
            for (int i = 0; i <= chunkCount; i++) {
                int max = 4000 * (i + 1);
                if (max >= sb.length()) {
                    Log.d(TAG, " >> " + sb.substring(4000 * i));
                } else {
                    Log.d(TAG, " >> " + sb.substring(4000 * i, max));
                }
            }
        } else {
            Log.d(TAG, " >> " + sb.toString());
        }
    }

    public static void ERROR(String message) {
        Log.e(TAG, " >> " + message);
    }

    public static void showSnackBar(Context context, ViewGroup layout, String msg) {
        Snackbar snackbar = Snackbar.make(layout, msg, Snackbar.LENGTH_LONG);
        View snackView = snackbar.getView();
        snackView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
        snackbar.show();
    }

    public static void showErrorDialog(Context ctx, String btnText, String message) {
        final Dialog dialog = new Dialog(ctx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_single_button);
        Button dialogButton = (Button) dialog.findViewById(R.id.btn_ok);
        TextView textMessage = (TextView) dialog.findViewById(R.id.text_message);
        textMessage.setText(message);
        dialogButton.setText(btnText);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
