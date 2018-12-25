package com.app.aquahey.purepani.viewmodel

import android.databinding.BaseObservable
import com.app.aquahey.purepani.model.NumberVerify
import com.app.aquahey.purepani.view.OnDataLoadCallBack
import com.nussd.todo.network.RetrofitBase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MobileVerifyModelView() : BaseObservable() {


    fun setData(onDataLoadCallBack: OnDataLoadCallBack, mobile: String?) {

        val api = RetrofitBase.create()
        val call = api.getMobileNoVerify(mobile)

        call.enqueue(object : Callback<NumberVerify> {
            override fun onResponse(call: Call<NumberVerify>, response: Response<NumberVerify>) {
                if (response.isSuccessful) {
                    val dataResponse = response.body()
                    if (dataResponse?.mobile != null) {

                        onDataLoadCallBack.onSuccess()
                    } else {
                        onDataLoadCallBack.onFailed("No Brand Found")
                    }
                } else {
                    onDataLoadCallBack.onFailed("Network Problem")
                }
            }

            override fun onFailure(call: Call<NumberVerify>, t: Throwable) {
                onDataLoadCallBack.onFailed(t.message)
            }
        })


    }

}
