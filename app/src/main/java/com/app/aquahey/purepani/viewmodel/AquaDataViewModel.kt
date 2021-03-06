package com.app.aquahey.purepani.viewmodel

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.Bindable
import com.app.aquahey.purepani.BR
import com.app.aquahey.purepani.adapter.ProductAdapter
import com.app.aquahey.purepani.model.Product
import com.app.aquahey.purepani.model.ProductResponse
import com.app.aquahey.purepani.view.OnDataLoadCallBack
import com.app.aquahey.purepani.view.OnItemClickCallBack
import com.nussd.todo.network.RetrofitBase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class AquaDataViewModel(val context: Context?, onItemClickCallBack: OnItemClickCallBack) : BaseObservable() {
    @get:Bindable
    var data: List<Product>
    @get:Bindable
    val adapter: ProductAdapter

    init {
        this.data = ArrayList()
        this.adapter = ProductAdapter(context, onItemClickCallBack)
    }

    fun setData(onDataLoadCallBack: OnDataLoadCallBack, pincode: String, productType: Int, isBrand: Int, city: String, state: String) {

        val api = RetrofitBase.create()
        val call = api.product(pincode, productType, isBrand, city, state)

        call.enqueue(object : Callback<ProductResponse> {
            override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                if (response.isSuccessful) {
                    val dataResponse = response.body()
                    if (dataResponse?.data != null && dataResponse.data.isNotEmpty()) {
                        data = dataResponse.data
                        notifyPropertyChanged(BR.data)
                        onDataLoadCallBack.onSuccess()
                    } else {
                        onDataLoadCallBack.onFailed("No Data Found")
                    }
                } else {
                    onDataLoadCallBack.onFailed("Network Problem")
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                onDataLoadCallBack.onFailed(t.message)
            }
        })
    }
}

