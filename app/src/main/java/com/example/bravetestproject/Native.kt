package com.example.bravetestproject

object Native {
    init {
        System.loadLibrary("bravetestproject")
    }

    external fun getLog(name: String, timeStamp: String, value: String): String

}