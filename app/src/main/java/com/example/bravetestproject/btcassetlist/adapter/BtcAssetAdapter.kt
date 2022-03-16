package com.example.bravetestproject.btcassetlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bravetestproject.Constants
import com.example.bravetestproject.R
import com.example.bravetestproject.btcassetlist.model.BtcAsset
import com.example.bravetestproject.databinding.BtcAssetItemViewBinding
import java.math.RoundingMode
import java.text.DecimalFormat

class BtcAssetAdapter(private val btcAssetList: MutableList<BtcAsset>) :
    RecyclerView.Adapter<BtcAssetAdapter.BtcAssetViewHolder>() {

    private val df = DecimalFormat("#,###.##")
    private var filteredList = btcAssetList

    init {
        df.roundingMode = RoundingMode.CEILING
    }

    inner class BtcAssetViewHolder(private val binding: BtcAssetItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(btcAsset: BtcAsset) {
            binding.ivCoinIcon.setImageResource(btcAsset.icon)
            binding.tvCoinName.text = btcAsset.name
            binding.tvCoinAmountBtc.text =
                if (btcAsset.name == Constants.BITCOIN) "" else binding.root.context.getString(
                    R.string.amount_btc,
                    df.format(btcAsset.value.btc)
                )
            binding.tvCoinAmountUsd.text =
                binding.root.context.getString(R.string.amount_usd, df.format(btcAsset.value.usd))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BtcAssetViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.btc_asset_item_view, parent, false)
        val binding = BtcAssetItemViewBinding.bind(view)
        return BtcAssetViewHolder(binding)
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