package com.app.aquahey.purepani.viewmodel

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.Bindable
import com.app.aquahey.purepani.model.LoginResponse
import com.app.aquahey.purepani.model.SignIn
import com.app.aquahey.purepani.utils.LocalConfiq
import com.app.aquahey.purepani.view.OnDataLoadCallBack
import com.nussd.todo.network.RetrofitBase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupViewModel(private val signIn: SignIn?, private val onSignUpCallback: OnDataLoadCallBack, private val context: Context) : BaseObservable() {


    @Bindable
    fun getSignIn(): SignIn? {
        return signIn
    }


    fun signin() {
        val api = RetrofitBase.create()
        val call = api.signin(signIn)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val networkResponse = response.body()
                    if (null != networkResponse) {
                        if (networkResponse.status == 1) {
                            /* LocalConfiq.putString(context, LocalConfiq.USER_ID,
                                     networkResponse.data.id)
                             LocalConfiq.putString(context, LocalConfiq.USER_NAME,
                                     networkResponse.data.name)
                     */        LocalConfiq.putString(context, LocalConfiq.MOBILE,
                                    networkResponse.data.mobile)
                            LocalConfiq.putBoolean(context, LocalConfiq.IS_LOGIN, true)
                            onSignUpCallback.onSuccess()
                        } else {
                            onSignUpCallback.onFailed("Mobile Number Already Register")
                        }
                    }
                } else {
                    onSignUpCallback.onFailed("Server Problem")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                onSignUpCallback.onFailed(t.message)
            }
        })
    }


}
