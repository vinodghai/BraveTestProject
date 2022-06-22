package com.example.jetpackcomposeassignment.repository


import com.example.jetpackcomposeassignment.btcassetlist.model.BtcAssetPrice
import com.example.jetpackcomposeassignment.btcassetlist.response.BtcAssetCallback
import com.example.jetpackcomposeassignment.btcassetlist.response.BtcAssetResponse

//@Singleton
class NetworkRepository /*@Inject*/ constructor(/*private val services: Services*/) {

    fun fetchBtcAssetList(callback: BtcAssetCallback) {
        val btc = BtcAssetPrice(20000.0, 1.0)
        val eth = BtcAssetPrice(20000.0, 1.0)
        val bat = BtcAssetPrice(20000.0, 1.0)
        val binance = BtcAssetPrice(20000.0, 1.0)
        val btcAssetResponse = BtcAssetResponse(btc, eth, binance, bat)
        callback.onSuccess(btcAssetResponse)
    }
}