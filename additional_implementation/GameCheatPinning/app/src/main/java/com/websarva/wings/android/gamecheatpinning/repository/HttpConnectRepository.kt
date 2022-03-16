package com.websarva.wings.android.gamecheatpinning.repository

import android.util.Log
import com.websarva.wings.android.gamecheatpinning.BuildConfig
import com.websarva.wings.android.gamecheatpinning.model.EncodeResult
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
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
        // okhttpの定義
        val client = OkHttpClient.Builder().apply {
            certificatePinner(CertificatePinner.Builder().apply {
                add(BuildConfig.SERVER_HOST, "sha256/stpeNz7s4yZHGrv966oJooUnfC5Da1r9uIrzWWlKSjI=")
                add(BuildConfig.SERVER_HOST, "sha256/C5+lpZ7tcVwmwQIMcRtPbsQtWLABXhQzejna0wHFr8M=")
            }.build())
        }.build()

        // retrofitの定義
        val retrofit = Retrofit.Builder().apply {
            baseUrl(BuildConfig.SERVER_ADDRESS)
            client(client)
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