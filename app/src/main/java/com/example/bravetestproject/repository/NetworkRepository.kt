package com.example.bravetestproject.repository


import com.example.bravetestproject.btcassetlist.error.BraveError
import com.example.bravetestproject.btcassetlist.response.BtcAssetCallback
import com.example.bravetestproject.btcassetlist.response.BtcAssetResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkRepository {

    private val services: Services = Retrofit.Builder()
        .baseUrl("https://api.coingecko.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient())
        .build()
        .create(Services::class.java)

    fun fetchBtcAssetList(callback: BtcAssetCallback) {
        services.fetchBtcAssetList().enqueue(object : Callback<BtcAssetResponse> {
            override fun onResponse(
                call: Call<BtcAssetResponse>,
                response: Response<BtcAssetResponse>
            ) {
                if (response.body() != null)
                    callback.onSuccess(response.body()!!)
                else
                    onFailure(call, Exception("Null response received!"))
            }

            override fun onFailure(call: Call<BtcAssetResponse>, t: Throwable) {
                callback.onError(BraveError("Could not retrieve currencies!"))
            }
        })
    }
}