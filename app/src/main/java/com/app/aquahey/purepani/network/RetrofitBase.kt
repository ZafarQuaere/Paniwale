package com.nussd.todo.network

import com.app.aquahey.purepani.utils.Constant
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by ikram-tiss on 24/1/18.
 */
class RetrofitBase {


    companion object Factory {
        var gson = GsonBuilder()
                .setLenient()
                .create()

        var client = OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS).build()

        fun create(): ApiInterface {

            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(Constant.BASE_URL).client(client)
                    .build()

            return retrofit.create(ApiInterface::class.java)
        }

        /*fun ChannelPlaylist(): ApiInterface {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Constant.BASE_URL_YOUTUBE)
                    .build()

            return retrofit.create(ApiInterface::class.java)
        }
    */
    }


}