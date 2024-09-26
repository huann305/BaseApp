package com.huann305.baseapp

import android.app.Application
import android.content.IntentFilter
import android.net.ConnectivityManager
import com.huann305.baseapp.util.NetworkReceiver

class App: Application() {
    private val networkReceiver = NetworkReceiver()
    override fun onCreate() {
        super.onCreate()
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkReceiver, intentFilter)
    }

    override fun onTerminate() {
        super.onTerminate()
        unregisterReceiver(networkReceiver)
    }
}