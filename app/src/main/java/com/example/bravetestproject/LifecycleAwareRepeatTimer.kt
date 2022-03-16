package com.example.bravetestproject

import android.os.CountDownTimer
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class LifecycleAwareRepeatTimer(millisInFuture: Long, val callback: TimerCallback) :
    DefaultLifecycleObserver {

    val timer = object : CountDownTimer(millisInFuture, 1000) {

        override fun onTick(millisUntilFinished: Long) {
            callback.onTick(millisUntilFinished)
        }

        override fun onFinish() {
            callback.onFinish()
            this.start()
        }
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        timer.start()
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        timer.cancel()
    }
}