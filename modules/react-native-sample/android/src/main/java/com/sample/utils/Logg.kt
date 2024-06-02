package com.sample.utils

import android.util.Log
import com.sample.BuildConfig

object Logg {
    private fun execLog(tag: String, message: String, type: Int) {
        if (BuildConfig.DEBUG) {
            when (type) {
                1 -> Log.d(tag, message)
                2 -> Log.i(tag, message)
                3 -> Log.w(tag, message)
                4 -> Log.e(tag, message)
                5 -> Log.v(tag, message)
            }
        }
    }

    fun d(tag: String, message: String) {
        execLog(tag, message, 1)
    }

    fun i(tag: String, message: String) {
        execLog(tag, message, 2)
    }

    fun w(tag: String, message: String) {
        execLog(tag, message, 3)
    }

    fun e(tag: String, message: String) {
        execLog(tag, message, 4)
    }

    fun v(tag: String, message: String) {
        execLog(tag, message, 5)
    }
}