package com.example.bravetestproject.btcassetlist.response

import com.example.bravetestproject.Constants
import com.example.bravetestproject.R
import com.example.bravetestproject.btcassetlist.model.BtcAsset
import com.example.bravetestproject.btcassetlist.model.BtcAssetPrice
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
            BtcAsset(Constants.BITCOIN, bitcoin, R.drawable.ic_btc),
            BtcAsset(Constants.ETHEREUM, ethereum, R.drawable.ic_eth),
            BtcAsset(Constants.BINANCE_COIN, binancecoin, R.drawable.ic_bnb),
            BtcAsset(Constants.BAT, bat, R.drawable.ic_bat)
        )
}