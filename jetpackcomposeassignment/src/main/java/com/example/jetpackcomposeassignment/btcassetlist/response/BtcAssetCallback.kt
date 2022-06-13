package com.example.jetpackcomposeassignment.btcassetlist.response

import com.example.jetpackcomposeassignment.btcassetlist.error.BraveError

interface BtcAssetCallback {

    fun onSuccess(response: BtcAssetResponse)

    fun onError(error: BraveError)
}