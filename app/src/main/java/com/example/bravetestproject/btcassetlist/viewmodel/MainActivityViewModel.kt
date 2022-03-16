package com.example.bravetestproject.btcassetlist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bravetestproject.Logger
import com.example.bravetestproject.Native
import com.example.bravetestproject.ResponseState
import com.example.bravetestproject.btcassetlist.LogEntry
import com.example.bravetestproject.btcassetlist.error.BraveError
import com.example.bravetestproject.btcassetlist.response.BtcAssetCallback
import com.example.bravetestproject.btcassetlist.response.BtcAssetResponse
import com.example.bravetestproject.repository.NetworkRepository
import java.lang.Exception
import java.sql.Timestamp

class MainActivityViewModel : ViewModel() {

    val btcAssetResponseLiveData: MutableLiveData<ResponseState<BtcAssetResponse>> =
        MutableLiveData(ResponseState.INITIAL())

    fun fetchBtcAssets() {
        btcAssetResponseLiveData.value = ResponseState.LOADING()
        NetworkRepository.fetchBtcAssetList(object : BtcAssetCallback {
            override fun onSuccess(response: BtcAssetResponse) {
                try {
                val timeStamp = Timestamp(System.currentTimeMillis()).toString()
                Logger.log(LogEntry(Native.getLog("Bitcoin", timeStamp, response.bitcoin.usd.toString())))
                Logger.log(LogEntry(Native.getLog("Ethereum", timeStamp, response.ethereum.usd.toString())))
                Logger.log(LogEntry(Native.getLog("BinanceCoin", timeStamp, response.binancecoin.usd.toString())))
                Logger.log(LogEntry(Native.getLog("Basic-Attention-Coin", timeStamp, response.bat.usd.toString())))
                } catch (e: Exception) {
                    // exception
                }
                btcAssetResponseLiveData.value = ResponseState.SUCCESS(response)
            }

            override fun onError(error: BraveError) {
                btcAssetResponseLiveData.value = ResponseState.FAILURE(error)
            }
        })
    }
}