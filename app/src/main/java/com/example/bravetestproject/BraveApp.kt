package com.example.bravetestproject

import android.app.Application

class BraveApp : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        private lateinit var INSTANCE: BraveApp

        fun getAppContext() = INSTANCE.applicationContext
    }
}