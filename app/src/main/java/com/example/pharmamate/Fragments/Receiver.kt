package com.example.pharmamate.Fragments

import com.example.pharmamate.R
import android.Manifest
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class Receiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val mp = MediaPlayer.create(context, R.raw.alarm)
        Log.d("Hello", "repeating alarm")
        mp.start()
        Toast.makeText(context, "Alarm Ringing", Toast.LENGTH_LONG).show()
    }
}
