package com.example.jetpackcomposeassignment.btcassetlist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackcomposeassignment.Constants
import com.example.jetpackcomposeassignment.R
import com.example.jetpackcomposeassignment.btcassetlist.model.BtcAsset
import java.math.RoundingMode
import java.text.DecimalFormat

class BtcAssetAdapter(private val btcAssetList: MutableList<BtcAsset>) :
    RecyclerView.Adapter<BtcAssetAdapter.BtcAssetViewHolder>() {

    private val df = DecimalFormat("#,###.##")
    private var filteredList = btcAssetList

    init {
        df.roundingMode = RoundingMode.CEILING
    }

    inner class BtcAssetViewHolder(/*private val binding: BtcAssetItemViewBinding*/view: View) :
        RecyclerView.ViewHolder(view) {
        val ivCoinIcon: AppCompatImageView
        val tvCoinName: AppCompatTextView
        val tvCoinAmountBtc: AppCompatTextView
        val tvCoinAmountUsd: AppCompatTextView

        init {
            ivCoinIcon = view.findViewById(R.id.ivCoinIcon)
            tvCoinName = view.findViewById(R.id.tvCoinName)
            tvCoinAmountBtc = view.findViewById(R.id.tvCoinAmountBtc)
            tvCoinAmountUsd = view.findViewById(R.id.tvCoinAmountUsd)
        }

        fun onBind(btcAsset: BtcAsset) {
            ivCoinIcon.setImageResource(btcAsset.icon)
            tvCoinName.text = btcAsset.name
            tvCoinAmountBtc.text =
                if (btcAsset.name == Constants.BITCOIN) "" else itemView.context.getString(
                    R.string.amount_btc,
                    df.format(btcAsset.value.btc)
                )
            tvCoinAmountUsd.text =
                itemView.context.getString(R.string.amount_usd, df.format(btcAsset.value.usd))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BtcAssetViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.btc_asset_item_view, parent, false)
        // val binding = BtcAssetItemViewBinding.bind(view)
        return BtcAssetViewHolder(view)
    }

    override fun onBindViewHolder(holder: BtcAssetViewHolder, position: Int) {
        holder.onBind(filteredList[position])
    }

    override fun getItemCount(): Int = filteredList.size

    fun filter(text: String) {
        val count = itemCount
        filteredList =
            btcAssetList.filter { text.isBlank() || it.name.lowercase().contains(text.lowercase()) }
                .toMutableList()
        notifyItemRangeChanged(0, count)
    }

    fun sort() {
        filteredList.sortWith { a, b ->
            when {
                a.value.usd == b.value.usd -> 0
                a.value.usd > b.value.usd -> 1
                else -> -1
            }
        }
        notifyItemRangeChanged(0, filteredList.size)
    }
}