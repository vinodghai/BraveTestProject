package com.example.bravetestproject.btcassetlist.response

import com.example.bravetestproject.btcassetlist.error.BraveError

interface BtcAssetCallback {

    fun onSuccess(response: BtcAssetResponse)

    fun onError(error: BraveError)
}