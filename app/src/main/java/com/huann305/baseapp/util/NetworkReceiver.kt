package com.huann305.baseapp.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast

class NetworkReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null) {
            if (isNetworkAvailable(context)) {
                Toast.makeText(context, "Kết nối mạng đã được bật", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Kết nối mạng đã bị mất", Toast.LENGTH_SHORT).show()
            }
        }
    }
}