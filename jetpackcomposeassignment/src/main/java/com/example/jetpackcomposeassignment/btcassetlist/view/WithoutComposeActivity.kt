package com.example.jetpackcomposeassignment.btcassetlist.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.widget.doOnTextChanged
import com.example.jetpackcomposeassignment.*
import com.example.jetpackcomposeassignment.btcassetlist.adapter.BtcAssetAdapter
import com.example.jetpackcomposeassignment.btcassetlist.viewmodel.BtcAssetsViewModel
import com.example.jetpackcomposeassignment.databinding.ActivityWithoutComposeBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WithoutComposeActivity : AppCompatActivity(), TimerCallback {
    private var _binding: ActivityWithoutComposeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BtcAssetsViewModel by viewModels()
    private var adapter: BtcAssetAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWithoutComposeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setupResponseStates()
        viewModel.fetchBtcAssets()
        setSearchBehavior()
        setMenu()
        setFloatingActionButtonBehavior()
        this.lifecycle.addObserver(LifecycleAwareRepeatTimer(5000, this))
    }

    private fun setSearchBehavior() {
        binding.toolbar.ivSearch.setOnClickListener {
            with(binding.toolbar.etSearch) {
                visibility = View.VISIBLE
                requestFocus()
                doOnTextChanged { text, _, _, _ ->
                    adapter?.filter(
                        text.toString()
                    )
                }
                setOnEditorActionListener { _, actionId, event ->
                    if (actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
                        Util.hideKeyboard(this@WithoutComposeActivity)
                        binding.toolbar.etSearch.visibility = View.GONE
                        binding.toolbar.etSearch.text?.clear()
                    }
                    false
                }
            }
        }
    }

    private fun setMenu() {
        binding.toolbar.ivMenu.setOnClickListener {
            val popupMenu = PopupMenu(this@WithoutComposeActivity, binding.toolbar.ivMenu)
            popupMenu.menuInflater.inflate(R.menu.option_menu, popupMenu.getMenu())
            popupMenu.setOnMenuItemClickListener {
                adapter?.sort()
                true
            }
            popupMenu.show()
        }
    }

    private fun setFloatingActionButtonBehavior() {
        binding.fab.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://brave.com/wallet/"))
            browserIntent.resolveActivity(packageManager)?.let { startActivity(browserIntent) }
        }
    }

    private fun setupResponseStates() {
        viewModel.btcAssetResponseLiveData.observe(this) {
            when (it) {
                is ResponseState.INITIAL -> {
                    binding.groupContent.visibility = View.VISIBLE
                    binding.progressCircular.visibility = View.GONE
                    binding.toolbar.ivMenu.visibility = View.VISIBLE
                    binding.toolbar.ivSearch.visibility = View.VISIBLE
                }

                is ResponseState.LOADING -> {
                    binding.groupContent.visibility = View.GONE
                    binding.progressCircular.visibility = View.VISIBLE
                    binding.toolbar.ivMenu.visibility = View.GONE
                    binding.toolbar.ivSearch.visibility = View.GONE
                }

                is ResponseState.FAILURE -> {
                    binding.groupContent.visibility = View.GONE
                    binding.progressCircular.visibility = View.GONE
                    binding.toolbar.ivMenu.visibility = View.GONE
                    binding.toolbar.ivSearch.visibility = View.GONE
                    if (adapter == null)
                        Toast.makeText(this, it.error.message, Toast.LENGTH_LONG).show()
                }

                is ResponseState.SUCCESS -> {
                    binding.groupContent.visibility = View.VISIBLE
                    binding.progressCircular.visibility = View.GONE
                    binding.toolbar.ivMenu.visibility = View.VISIBLE
                    binding.toolbar.ivSearch.visibility = View.VISIBLE
                    if (adapter == null) {
                        adapter = BtcAssetAdapter(it.response.btcAssetList)
                        binding.rvBtcAssetList.adapter = adapter
                    } else
                        adapter?.let { it.notifyItemRangeChanged(0, it.itemCount) }
                }
            }
        }
    }

    override fun onFinish() {
        super.onFinish()
        viewModel.fetchBtcAssets()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}