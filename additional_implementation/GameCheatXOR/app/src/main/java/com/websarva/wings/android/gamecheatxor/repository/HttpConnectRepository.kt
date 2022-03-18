package com.websarva.wings.android.gamecheatxor.repository

import android.util.Log
import com.websarva.wings.android.gamecheatxor.model.EncodeResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Inject

interface HttpConnectService{
    @POST("/")
    fun postJsonRequestForIndex(@Body result: EncodeResult): Call<Void>
}

interface HttpConnectRepository {
    fun connect(result: EncodeResult, listener: (result: Boolean) -> Unit)
}

class HttpConnectRepositoryClient @Inject constructor(): HttpConnectRepository {
    override fun connect(result: EncodeResult, listener: (result: Boolean) -> Unit) {
        // retrofitの定義
        val retrofit = Retrofit.Builder().apply {
            baseUrl("http://192.168.150.70:8080")
            addConverterFactory(GsonConverterFactory.create())
        }.build()
        // データの送信
        retrofit.create(HttpConnectService::class.java).also { service ->
            service.postJsonRequestForIndex(result).also {
                it.enqueue(object: Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        Log.i("onResponse", "Success!!")
                        listener(true)
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Log.w("Warning", t.message.toString())
                        listener(false)
                    }

                })
            }
        }
    }
}