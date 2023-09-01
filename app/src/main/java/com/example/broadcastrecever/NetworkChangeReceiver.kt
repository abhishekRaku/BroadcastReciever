package com.example.broadcastrecever

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.ConnectivityManager.CONNECTIVITY_ACTION
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar


class NetworkChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected) {
            Log.d("NetworkChangeReceiver", "Network is connected")
            Toast.makeText(context,"Network is connected",Toast.LENGTH_SHORT).show()
        } else {
            Log.d("NetworkChangeReceiver", "Network is disconnected")
            Toast.makeText(context,"Network is disconnected",Toast.LENGTH_SHORT).show()
        }

    }

    }