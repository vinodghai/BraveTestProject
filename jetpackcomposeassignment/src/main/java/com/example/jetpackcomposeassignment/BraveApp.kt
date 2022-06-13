package com.example.jetpackcomposeassignment

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BraveApp : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        private lateinit var INSTANCE: BraveApp

        fun getAppContext(): Context = INSTANCE.applicationContext
    }
}