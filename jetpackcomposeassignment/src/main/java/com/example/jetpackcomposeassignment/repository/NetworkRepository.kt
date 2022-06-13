package com.example.jetpackcomposeassignment.repository


import com.example.jetpackcomposeassignment.btcassetlist.error.BraveError
import com.example.jetpackcomposeassignment.btcassetlist.response.BtcAssetCallback
import com.example.jetpackcomposeassignment.btcassetlist.response.BtcAssetResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkRepository @Inject constructor(private val services: Services) {

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