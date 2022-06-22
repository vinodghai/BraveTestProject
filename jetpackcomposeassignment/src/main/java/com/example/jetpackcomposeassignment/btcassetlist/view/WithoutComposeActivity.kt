package com.example.jetpackcomposeassignment.btcassetlist.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.PopupMenu
import androidx.constraintlayout.widget.Group
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackcomposeassignment.*
import com.example.jetpackcomposeassignment.btcassetlist.adapter.BtcAssetAdapter
import com.example.jetpackcomposeassignment.btcassetlist.viewmodel.BtcAssetsViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


//@AndroidEntryPoint
class WithoutComposeActivity : AppCompatActivity(), TimerCallback {
    /* private var _binding: ActivityWithoutComposeBinding? = null
     private val binding get() = _binding!!*/
    private val viewModel: BtcAssetsViewModel by viewModels()
    private var adapter: BtcAssetAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //_binding = ActivityWithoutComposeBinding.inflate(layoutInflater)
        setContentView(/*binding.root*/R.layout.activity_without_compose)
        supportActionBar?.hide()
        setupResponseStates()
        viewModel.fetchBtcAssets()
        setSearchBehavior()
        setMenu()
        setFloatingActionButtonBehavior()
        this.lifecycle.addObserver(LifecycleAwareRepeatTimer(5000, this))
    }

    private fun setSearchBehavior() {
        findViewById<AppCompatImageView>(R.id.ivSearch).setOnClickListener {
            with(findViewById<AppCompatEditText>(R.id.etSearch)) {
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
                        this.visibility = View.GONE
                        this.text?.clear()
                    }
                    false
                }
            }
        }
    }

    private fun setMenu() {
        findViewById<AppCompatImageView>(R.id.ivMenu).setOnClickListener {
            val popupMenu = PopupMenu(this@WithoutComposeActivity, it)
            popupMenu.menuInflater.inflate(R.menu.option_menu, popupMenu.getMenu())
            popupMenu.setOnMenuItemClickListener {
                adapter?.sort()
                true
            }
            popupMenu.show()
        }
    }

    private fun setFloatingActionButtonBehavior() {
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://brave.com/wallet/"))
            browserIntent.resolveActivity(packageManager)?.let { startActivity(browserIntent) }
        }
    }

    private fun setupResponseStates() {
        viewModel.btcAssetResponseLiveData.observe(this) {
            val groupContent = findViewById<Group>(R.id.groupContent)
            val progressCircular = findViewById<ProgressBar>(R.id.progressCircular)
            val ivMenu = findViewById<AppCompatImageView>(R.id.ivMenu)
            val ivSearch = findViewById<AppCompatImageView>(R.id.ivSearch)
            when (it) {
                is ResponseState.INITIAL -> {
                    groupContent.visibility = View.VISIBLE
                    progressCircular.visibility = View.GONE
                    ivMenu.visibility = View.VISIBLE
                    ivSearch.visibility = View.VISIBLE
                }

                is ResponseState.LOADING -> {
                    groupContent.visibility = View.GONE
                    progressCircular.visibility = View.VISIBLE
                    ivMenu.visibility = View.GONE
                    ivSearch.visibility = View.GONE
                }

                is ResponseState.FAILURE -> {
                    groupContent.visibility = View.GONE
                    progressCircular.visibility = View.GONE
                    ivMenu.visibility = View.GONE
                    ivSearch.visibility = View.GONE
                    if (adapter == null)
                        Toast.makeText(this, it.error.message, Toast.LENGTH_LONG).show()
                }

                is ResponseState.SUCCESS -> {
                    groupContent.visibility = View.VISIBLE
                    progressCircular.visibility = View.GONE
                    ivMenu.visibility = View.VISIBLE
                    ivSearch.visibility = View.VISIBLE
                    if (adapter == null) {
                        adapter = BtcAssetAdapter(it.response.btcAssetList)
                        findViewById<RecyclerView>(R.id.rvBtcAssetList).adapter = adapter
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
}