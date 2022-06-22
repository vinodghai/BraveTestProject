package com.example.jetpackcomposeassignment.repository


interface Services {

    // @GET("v3/simple/price?vs_currencies=usd,btc&ids=bitcoin,ethereum,binancecoin,basic-attention-token")
    fun fetchBtcAssetList()//: Call<BtcAssetResponse>
}