package com.example.jetpackcomposeassignment.repository


import com.example.jetpackcomposeassignment.btcassetlist.response.BtcAssetResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Services {

    @GET("v3/simple/price?vs_currencies=usd,btc&ids=bitcoin,ethereum,binancecoin,basic-attention-token")
    fun fetchBtcAssetList(): Call<BtcAssetResponse>
}