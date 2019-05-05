package com.app.aquahey.purepani.viewmodel

import android.content.Context
import android.databinding.BaseObservable
import com.app.aquahey.purepani.model.OtpResponse
import com.app.aquahey.purepani.network.NetworkResponse
import com.app.aquahey.purepani.utils.LocalConfiq
import com.app.aquahey.purepani.utils.Utils
import com.app.aquahey.purepani.view.OnDataLoadCallBack
import com.nussd.todo.network.RetrofitBase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommanViewModel : BaseObservable() {


    fun otpService(context: Context, onDataLoadCallBack: OnDataLoadCallBack, mobile: String) {
        val api = RetrofitBase.create()
        val call = api.getOtp(mobile)

        call.enqueue(object : Callback<OtpResponse> {
            override fun onResponse(call: Call<OtpResponse>, response: Response<OtpResponse>) {
                if (response.isSuccessful) {
                    val otpResponse = response.body()
                    if (null != otpResponse) {
                        if (otpResponse.status == "status") {
                            LocalConfiq.putString(context, LocalConfiq.OTP,
                                    Utils.getOTP(otpResponse.sms_text))

                            onDataLoadCallBack.onSuccess()
                        } else {
                            onDataLoadCallBack.onFailed("Invalid User ")
                        }
                    }
                } else {
                    onDataLoadCallBack.onFailed("Server Problem")
                }
            }

            override fun onFailure(call: Call<OtpResponse>, t: Throwable) {
                onDataLoadCallBack.onFailed(t.message)
            }
        })
    }

    fun changePasswordService(onDataLoadCallBack: OnDataLoadCallBack, mobile: String, pass: String) {
        val api = RetrofitBase.create()
        val call = api.changePassword(mobile, pass)

        call.enqueue(object : Callback<NetworkResponse> {
            override fun onResponse(call: Call<NetworkResponse>, response: Response<NetworkResponse>) {
                if (response.isSuccessful) {
                    val otpResponse = response.body()
                    if (null != otpResponse) {
                        if (otpResponse.success == 1) {

                            onDataLoadCallBack.onSuccess()
                        } else {
                            onDataLoadCallBack.onFailed("Invalid User ")
                        }
                    }
                } else {
                    onDataLoadCallBack.onFailed("Server Problem")
                }
            }

            override fun onFailure(call: Call<NetworkResponse>, t: Throwable) {
                onDataLoadCallBack.onFailed(t.message)
            }
        })
    }
}
