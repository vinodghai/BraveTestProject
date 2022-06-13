package com.example.bravetestproject.btcassetlist.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.bravetestproject.R
import com.example.jetpackcomposeassignment.btcassetlist.view.WithoutComposeActivity
import com.example.jetpackcomposeassignment.compose.CryptoComposeActivity



class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun openCompose(view: View) {
        startActivity(Intent(this, CryptoComposeActivity::class.java))
    }

    fun openWithoutCompose(view: View) {
        startActivity(Intent(this, WithoutComposeActivity::class.java))
    }
}