package com.app.aquahey.purepani.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.app.aquahey.purepani.view.OnDataLoadCallBack;

public class Utils {

    public static String getOTP(final String msg) {

        final OnDataLoadCallBack loadCallBack = new OnDataLoadCallBack() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailed(String error) {

            }
        };
        final String[] s = msg.split(" ");
        return s[0];
    }

    public static void rateUsOnPlayStore(Context context) {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }

    public static String getAppVersion(Context context) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "1.0";
        }
        ErrorUtils.DEBUG("PackageName = " + info.packageName + "\nVersionCode = " + info.versionCode + "\nVersionName = "
                + info.versionName/* + "\nPermissions = " + info.permissions*/);
        return info.versionName;
    }
}
