package com.app.aquahey.purepani.utils

import android.content.Context

/**
 * Created by ikram-tiss on 27/12/17.
 */
class LocalConfiq {
    var mypreference = "mypref"


    companion object {
        var mypreference = "mypref"
        @JvmStatic
        var IS_LOGIN = "isLogin"
        var USER_ID = "userId"
        var USER_NAME = "userName"
        var MOBILE = "mobile"
        var OTP = "otp"
        var PINCODE = "pincode"

        var IS_ADDRESS = "isAddress"

        @JvmStatic
        fun putBoolean(context: Context, key: String, isInList: Boolean) {
            val sharedpreferences = context.getSharedPreferences(mypreference,
                    Context.MODE_PRIVATE)
            val editor = sharedpreferences.edit()
            editor.putBoolean(key, isInList)
            editor.commit()
        }

        @JvmStatic
        fun getBoolean(context: Context, key: String): Boolean {
            val sharedpreferences = context.getSharedPreferences(mypreference,
                    Context.MODE_PRIVATE)
            return sharedpreferences.getBoolean(key, false)
        }

        @JvmStatic
        fun putString(context: Context, key: String, isInList: String) {
            val sharedpreferences = context.getSharedPreferences(mypreference,
                    Context.MODE_PRIVATE)
            val editor = sharedpreferences.edit()
            editor.putString(key, isInList)
            editor.commit()
        }

        @JvmStatic
        fun getString(context: Context?, key: String): String {
            val sharedpreferences = context!!.getSharedPreferences(mypreference,
                    Context.MODE_PRIVATE)
            return sharedpreferences.getString(key, "")
        }

        @JvmStatic
        fun getUserId(context: Context): String {
            val sharedpreferences = context.getSharedPreferences(mypreference,
                    Context.MODE_PRIVATE)
            return sharedpreferences.getString(USER_ID, "")
        }

        @JvmStatic
        fun getMobile(context: Context): String {
            val sharedpreferences = context.getSharedPreferences(mypreference,
                    Context.MODE_PRIVATE)
            return sharedpreferences.getString(MOBILE, "")
        }

        @JvmStatic
        fun getUserName(context: Context): String {
            val sharedpreferences = context.getSharedPreferences(mypreference,
                    Context.MODE_PRIVATE)
            return sharedpreferences.getString(USER_NAME, "")
        }
    }
}