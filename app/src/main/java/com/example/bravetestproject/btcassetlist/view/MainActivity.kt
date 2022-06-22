package com.example.bravetestproject.btcassetlist.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.bravetestproject.R
import com.example.jetpackcomposeassignment.btcassetlist.view.WithoutComposeActivity


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun openWithoutCompose(view: View) {
        startActivity(Intent(this, WithoutComposeActivity::class.java))
    }
}