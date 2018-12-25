package com.app.aquahey.purepani.utils;

import com.app.aquahey.purepani.view.OnDataLoadCallBack;

public class Utils {

    public static String getOTP(final String msg) {

     final OnDataLoadCallBack loadCallBack=   new OnDataLoadCallBack() {
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
}
