package com.example.broadcastrecever

import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi

class DNDReceiver: BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context?, intent: Intent?) {

        if(intent?.action.equals(NotificationManager.ACTION_INTERRUPTION_FILTER_CHANGED)){
            val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            var msg = ""
            if(notificationManager.currentInterruptionFilter == NotificationManager.INTERRUPTION_FILTER_ALARMS) {
                msg = NotificationManager.INTERRUPTION_FILTER_ALARMS.toString()
            } else if (notificationManager.currentInterruptionFilter == NotificationManager.INTERRUPTION_FILTER_NONE) {
                msg = NotificationManager.INTERRUPTION_FILTER_NONE.toString()
            } else if (notificationManager.currentInterruptionFilter == NotificationManager.INTERRUPTION_FILTER_ALL) {
                msg = NotificationManager.INTERRUPTION_FILTER_ALL.toString()
            } else if (notificationManager.currentInterruptionFilter == NotificationManager.INTERRUPTION_FILTER_PRIORITY) {
                msg = NotificationManager.INTERRUPTION_FILTER_PRIORITY.toString()
            } else if (notificationManager.currentInterruptionFilter == NotificationManager.INTERRUPTION_FILTER_UNKNOWN) {
               msg = NotificationManager.INTERRUPTION_FILTER_UNKNOWN.toString()
            }
            Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
        }

    }
}