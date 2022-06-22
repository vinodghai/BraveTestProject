package com.example.jetpackcomposeassignment

import android.app.Application
import android.content.Context
import com.example.jetpackcomposeassignment.repository.NetworkRepository

//@HiltAndroidApp
class BraveApp : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {

        val networkRepository = NetworkRepository()

        private lateinit var INSTANCE: BraveApp

        fun getAppContext(): Context = INSTANCE.applicationContext
    }
}