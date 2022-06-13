package com.example.jetpackcomposeassignment.btcassetlist.response

import com.example.jetpackcomposeassignment.Constants
import com.example.jetpackcomposeassignment.R
import com.example.jetpackcomposeassignment.btcassetlist.model.BtcAsset
import com.example.jetpackcomposeassignment.btcassetlist.model.BtcAssetPrice
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class BtcAssetResponse(
    val bitcoin: BtcAssetPrice,
    val ethereum: BtcAssetPrice,
    val binancecoin: BtcAssetPrice,
    @SerializedName("basic-attention-token") val bat: BtcAssetPrice
) : Serializable {
    val btcAssetList
        get() = mutableListOf(
            BtcAsset(Constants.BITCOIN, bitcoin, R.drawable.ic_btc_ok),
            BtcAsset(Constants.ETHEREUM, ethereum, R.drawable.ic_eth_ok),
            BtcAsset(Constants.BINANCE_COIN, binancecoin, R.drawable.ic_bnb_ok),
            BtcAsset(Constants.BAT, bat, R.drawable.ic_bat_ok)
        )
}