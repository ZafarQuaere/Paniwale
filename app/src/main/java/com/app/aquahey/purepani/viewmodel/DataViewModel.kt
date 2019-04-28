package com.nussd.emptodo.viewModel

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.support.v4.app.FragmentActivity
import com.app.aquahey.purepani.BR
import com.app.aquahey.purepani.adapter.DataAdapter
import com.app.aquahey.purepani.model.BrandData
import com.nussd.todo.network.RetrofitBase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class DataViewModel(val context: FragmentActivity?) : BaseObservable() {
    private lateinit var onLoadTaskCallback: OnLoadTaskCallback
    @get:Bindable
    var data: List<BrandData.Brand>
    @get:Bindable
    val adapter: DataAdapter

    init {
        this.data = ArrayList()
        this.adapter = DataAdapter(context)
    }

    fun setData(onLoadTaskCallback: OnLoadTaskCallback) {
        this.onLoadTaskCallback = onLoadTaskCallback

        val api = RetrofitBase.create()
        val call = api.getBrands()

        call.enqueue(object : Callback<BrandData> {
            override fun onResponse(call: Call<BrandData>, response: Response<BrandData>) {
                if (response.isSuccessful) {
                    val dataResponse = response.body()
                    if (dataResponse?.data != null && dataResponse.status == 1) {
                        data = dataResponse.data
                        notifyPropertyChanged(BR.data)
                        onLoadTaskCallback.onSuccess()
                    } else {
                        onLoadTaskCallback.onFailed("No Brand Found")
                    }
                } else {
                    onLoadTaskCallback.onFailed("Network Problem")
                }
            }

            override fun onFailure(call: Call<BrandData>, t: Throwable) {
                onLoadTaskCallback.onFailed(t.message)

                // Toast.makeText(applicationContext, "Error. " + t.message, Toast.LENGTH_LONG).show()
            }
        })


    }

    interface OnLoadTaskCallback {
        fun onSuccess()
        fun onFailed(error: String?)
    }


}
