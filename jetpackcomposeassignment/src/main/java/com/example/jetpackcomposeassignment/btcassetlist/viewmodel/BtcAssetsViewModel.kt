package com.example.jetpackcomposeassignment.btcassetlist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jetpackcomposeassignment.ResponseState
import com.example.jetpackcomposeassignment.btcassetlist.error.BraveError
import com.example.jetpackcomposeassignment.btcassetlist.response.BtcAssetCallback
import com.example.jetpackcomposeassignment.btcassetlist.response.BtcAssetResponse
import com.example.jetpackcomposeassignment.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BtcAssetsViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {

    val btcAssetResponseLiveData: MutableLiveData<ResponseState<BtcAssetResponse>> =
        MutableLiveData(ResponseState.INITIAL())

    fun fetchBtcAssets() {
        btcAssetResponseLiveData.value = ResponseState.LOADING()
        networkRepository.fetchBtcAssetList(object : BtcAssetCallback {
            override fun onSuccess(response: BtcAssetResponse) {
                try {
//                val timeStamp = Timestamp(System.currentTimeMillis()).toString()
////                Logger.log(LogEntry(Native.getLog("Bitcoin", timeStamp, response.bitcoin.usd.toString())))
////                Logger.log(LogEntry(Native.getLog("Ethereum", timeStamp, response.ethereum.usd.toString())))
////                Logger.log(LogEntry(Native.getLog("BinanceCoin", timeStamp, response.binancecoin.usd.toString())))
////                Logger.log(LogEntry(Native.getLog("Basic-Attention-Coin", timeStamp, response.bat.usd.toString())))
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