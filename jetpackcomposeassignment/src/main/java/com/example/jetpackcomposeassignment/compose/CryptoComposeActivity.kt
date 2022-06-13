package com.example.jetpackcomposeassignment.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposeassignment.*
import com.example.jetpackcomposeassignment.R
import com.example.jetpackcomposeassignment.btcassetlist.model.BtcAsset
import com.example.jetpackcomposeassignment.btcassetlist.model.BtcAssetPrice
import com.example.jetpackcomposeassignment.btcassetlist.viewmodel.BtcAssetsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.math.RoundingMode
import java.text.DecimalFormat

@AndroidEntryPoint
class CryptoComposeActivity : AppCompatActivity(), TimerCallback {

    private val viewModel: BtcAssetsViewModel by viewModels()
    private val df = DecimalFormat("#,###.##")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        df.roundingMode = RoundingMode.CEILING
        viewModel.fetchBtcAssets()
        this.lifecycle.addObserver(LifecycleAwareRepeatTimer(5000, this))
        setContent {
            MainScreen()
        }
    }

    @Composable
    fun MainScreen() {
        Box(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(8.dp)
                .background(Color(android.R.color.transparent))
        ) {
            when (val state = viewModel.btcAssetResponseLiveData.observeAsState().value) {
                is ResponseState.INITIAL -> {

                }
                is ResponseState.FAILURE -> {
                    Text(modifier = Modifier
                        .fillMaxSize(), text = state.error.message)
                }
                is ResponseState.LOADING -> CircularProgressIndicator(Modifier.fillMaxSize().align(
                    Alignment.Center))
                is ResponseState.SUCCESS -> {
                    BtcAssetList(btcAssetList = state.response.btcAssetList)
                }
                null -> Text(text = "Could not fetch")
            }
        }
    }

    @Composable
    fun CryptoCard(btcAsset: BtcAsset) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp, bottom = 6.dp)
        ) {
            Image(painter = painterResource(id = btcAsset.icon), contentDescription = "Crypto Icon")
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.Start)
                    .align(CenterVertically), text = btcAsset.name
            )
            Column(Modifier.align(CenterVertically)) {
                Text(
                    text = stringResource(R.string.amount_usd, df.format(btcAsset.value.usd))
                )
                if (btcAsset.name != Constants.BITCOIN)
                    Text(
                        text = stringResource(R.string.amount_btc, df.format(btcAsset.value.btc))
                    )
            }
        }
    }

    @Composable
    fun BtcAssetList(btcAssetList: MutableList<BtcAsset>) {
        LazyColumn {
            items(btcAssetList) { crypto -> CryptoCard(crypto) }
        }
    }

    @Preview
    @Composable
    fun PreviewCryptoCard() {
        val bitcoin = BtcAssetPrice(1234.98, 12.0)
        CryptoCard(btcAsset = BtcAsset(Constants.BITCOIN, bitcoin, R.drawable.ic_btc_ok))
    }

    @Preview
    @Composable
    fun PreviewList() {
        val bitcoin = BtcAssetPrice(1234.98, 12.0)
        val btc = BtcAsset(Constants.BITCOIN, bitcoin, R.drawable.ic_btc_ok)
        val list = mutableListOf(
            btc,
            btc,
            btc
        )
        BtcAssetList(btcAssetList = list)
    }

    override fun onFinish() {
        super.onFinish()
        viewModel.fetchBtcAssets()
    }
}