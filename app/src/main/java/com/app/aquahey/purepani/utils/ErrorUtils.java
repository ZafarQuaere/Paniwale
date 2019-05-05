package com.app.aquahey.purepani.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Ikram on 11/8/2017.
 */

public class ErrorUtils {
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
}
